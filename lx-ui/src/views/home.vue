<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-home">
      <el-tabs v-model="activeName" class="mod-tabs">
        <el-tab-pane label="模型预测" name="first">
          <div class="prediction">
            <el-form ref="formRef" label-width="80px" :status-icon="true" :model="modeParams" :rules="rules">
              <el-form-item label="模型选择" prop="modelname">
                <el-select v-model="modelSelected" placeholder="选择" style="width: 240px">
                  <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" :disabled="item.disabled" />
                </el-select>
              </el-form-item>
              <el-form-item label="图片选择" class="mode-params">
                <el-radio-group v-model="radio1" @change="radiochange">
                  <el-radio label="1">单个模式</el-radio>
                  <el-radio label="2" disabled>批量模式</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-form>
          </div>
          <div class="container">
            <div>
              <el-card>
                <el-upload
                  ref="upload"
                  class="uploader"
                  :action="uploadUrl"
                  :on-preview="handlePictureCardPreview"
                  :on-remove="handleRemove"
                  :on-success="uploadSuccess"
                  :on-error="uploadError"
                  :before-upload="beforeUpload"
                  :limit="1"
                  :on-exceed="handleExceed"
                  name="uploadFile"
                >
                  <img v-if="imageUrl" :src="imageUrl" class="image" />
                  <el-icon v-else class="image-uploader-icon"><Plus /></el-icon>
                </el-upload>
                <el-dialog v-model="dialogVisible">
                  <img w-full style="width: 340px; height: 340px" :src="dialogImageUrl" alt="图像预览" />
                </el-dialog>
              </el-card>
            </div>
            <div class="arrow">
              <el-icon size="20" style="margin-right: 5px"><DArrowLeft /></el-icon>
              <el-icon size="20" style="margin-left: 5px"><DArrowRight /></el-icon>
            </div>
            <div v-loading="loading">
              <el-card style="max-width: 480px">
                <el-image style="width: 340px; height: 340px" :src="resultUrl" :zoom-rate="1.2" :max-scale="7" :min-scale="0.2" :preview-src-list="previewList" :initial-index="4" fit="cover" />
              </el-card>
            </div>
          </div>
          <el-divider />
          <div class="operation">
            <el-button type="primary" color="#017BFF" @click="onStart" :disabled="loading">开始计算</el-button>
            <el-button @click="onClear">清除计算</el-button>
            <el-button type="primary" color="#2FC060" @click="onRecord" :disabled="isCaled" :plain="!isCaled">成果入库</el-button>
          </div>
        </el-tab-pane>

        <el-tab-pane label="模型注册" name="second">
          <div>
            <span>模型名称：</span><el-input style="width: 240px" placeholder="请输入" :suffix-icon="Search" />
            <span class="reg-span">模型分类：</span>
            <el-select placeholder="请选择" style="width: 240px">
              <el-option v-for="item in regOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
            <span class="reg-span">注册日期：</span>
            <el-date-picker type="date" placeholder="请选择" />
            <el-button type="primary" :icon="SetUp" class="regist-model">注册模型</el-button>
          </div>
          <div></div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </el-card>
</template>

<style scoped>
.mod-home {
  line-height: 1.5;
}
.prediction {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}
.mode-params {
  align-items: center; /* 垂直居中 */
}
.container {
  display: flex; /* 使用Flexbox布局 */
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  margin-top: 10px;
}
.container .uploader .image {
  width: 340px;
  height: 340px;
  display: block;
}
.container .arrow {
  border: 1px solid darkgray;
  border-radius: 10%;
  margin-left: 20px;
  margin-right: 20px;
  display: flex;
  justify-content: center;
  padding: 5px;
  color: gray;
}
.operation {
  display: flex; /* 使用Flexbox布局 */
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
}
.reg-span {
  margin-left: 20px;
}
.regist-model {
  float: right;
}
</style>
<style>
.uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.image-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 340px;
  height: 340px;
  text-align: center;
}
</style>

<script setup lang="ts">
import { ref } from "vue";
import { reactive } from "vue";
import { DArrowRight, View, Search, SetUp } from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from "element-plus";
import app from "@/constants/app";
import { genFileId } from "element-plus";
import type { Action } from "element-plus";
import type { UploadInstance, UploadProps, UploadRawFile } from "element-plus";
import baseService from "@/service/baseService";
import { el, tr } from "element-plus/es/locale";

const loading = ref(false);

// 上传接口
const uploadUrl = `${app.api}/model/upload`;
const upload = ref<UploadInstance>();

const activeName = ref("first");
const modelSelected = ref("veg");
const radio1 = ref("1");
const radiochange = (value: string) => {
  console.log("当前选中的值：", value);
};
// mode：0表示单张，1表示批量
const rules = ref({
  modelname: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  mode: [{ required: true, message: "必填项不能为空", trigger: "blur" }]
});
const modeParams = reactive({ modelname: "", radiomode: "" });

//成果入库按钮状态
const isCaled = ref(true);

// 选择模型
const options = [
  {
    value: "veg",
    label: "植被提取模型",
    disabled: false
  },
  {
    value: "road",
    label: "道路提取模型",
    disabled: false
  }
];
// 模型注册-选择模型
const regOptions = [
  {
    value: "model",
    label: "全部",
    disabled: false
  }
];

// 图像处理
const dialogImageUrl = ref("");
const dialogVisible = ref(false);
const imageUrl = ref("");
const FAILURL = "http://39.105.106.56:8083/ruoergai-files/image.png";
const resultUrl = ref(FAILURL); //识别结果
const previewList = ref([""]); //预览图像
const oriImgId = ref(""); //上传图像的id
const oriSavNam = ref(""); //上传图像在服务器文件名

const uploadSuccess: UploadProps["onSuccess"] = (response, uploadFile) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw!);
  oriImgId.value = response.slice(0, response.indexOf("@"));
  oriSavNam.value = response.slice(response.indexOf("@") + 1);
  console.log("上传成功，图像id:", oriImgId.value);
};
const uploadError: UploadProps["onError"] = (response) => {
  ElMessage.error("图像上传失败");
};

const beforeUpload: UploadProps["beforeUpload"] = (rawFile) => {
  if (["image/jpeg", "image/png", "image/tif", "image/tiff"].indexOf(rawFile.type) === -1) {
    ElMessage.error("请上传jpg、png、tif、tiff图像格式");
    return false;
  }

  return true;
};

const handleExceed: UploadProps["onExceed"] = (files) => {
  ElMessageBox.alert("请先删除已上传图像", "提示", {
    // if you want to disable its autofocus
    // autofocus: false,
    confirmButtonText: "OK"
    // callback: (action: Action) => {
    //   ElMessage({
    //     type: 'info',
    //     message: `action: ${action}`,
    //   })
    // },
  });
};

const handleRemove: UploadProps["onRemove"] = (uploadFile, uploadFiles) => {
  console.log(uploadFile, uploadFiles);
  imageUrl.value = "";
  onClear();
};

const handlePictureCardPreview: UploadProps["onPreview"] = (uploadFile) => {
  dialogImageUrl.value = uploadFile.url!;
  dialogVisible.value = true;
};

// 开始计算图像
const onStart = () => {
  if (oriImgId.value.length) {
    let params = {
      id: oriImgId.value,
      saved_Name: oriSavNam.value
    };
    loading.value = true;
    baseService
      .post("/model/start", params)
      .then((res) => {
        if ("1" == res.data) {
          // 识别成功，展示识别结果图像
          resultUrl.value = "http://39.105.106.56:8083/ruoergai-files/a035570e-d20c-4f6d-a6dc-f93370985492.jpg";
          previewList.value[0] = resultUrl.value;
          isCaled.value = false;
        } else {
          ElMessage.error("处理发生异常");
        }
        loading.value = false;
      })
      .catch((res) => {
        loading.value = false;
        ElMessage.error(res.msg);
      });
  } else {
    ElMessage.error("未上传图像");
  }
};

// 清除计算
const onClear = () => {
  // 清空右侧
  resultUrl.value = FAILURL;
  previewList.value[0] = resultUrl.value;
  isCaled.value = true;
};

// 成果入库
const onRecord = () => {
  let params = {
    id: oriImgId.value
  };
  baseService
    .post("/model/record", params)
    .then(() => {
      ElMessage.success("入库成功！");
    })
    .catch((res) => {
      console.log(res)
      ElMessage.error("处理发生异常:", res.msg);
    });
};
</script>
