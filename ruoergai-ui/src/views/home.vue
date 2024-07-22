<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-home">
      <el-tabs v-model="activeName" class="mod-tabs">
        <el-tab-pane label="模型预测" name="first">
          <div class="prediction">
            <el-form ref="formRef" label-width="80px" :status-icon="true" :model="modeParams" :rules="rules">
              <el-form-item label="模型选择" prop="modelname">
                <el-select v-model="modelSelected" placeholder="Select" style="width: 240px">
                  <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" :disabled="item.disabled" />
                </el-select>
              </el-form-item>
              <el-form-item label="图片选择" prop="mode" class="mode-params">
                <el-radio-group v-model="modeParams.mode">
                  <el-radio value="0">单个模式</el-radio>
                  <el-radio value="1">批量模式</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-form>
          </div>
          <div>
            <el-upload
              class="uploader"
              action="https"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove"
              :on-success="uploadSuccess"
              :on-error="uploadError"
              :before-upload="beforeUpload"
              :show-file-list="false"
            >
              <img v-if="imageUrl" :src="imageUrl" class="image" />
              <el-icon v-else class="image-uploader-icon"><Plus /></el-icon>
            </el-upload>
            <el-dialog v-model="dialogVisible">
              <img w-full :src="dialogImageUrl" alt="图像预览" />
            </el-dialog>
          </div>
          <div><el-icon size="20"><DArrowLeft /></el-icon>    <el-icon size="20"><DArrowRight /></el-icon></div>
          <div>
            <el-image
      style="width: 100px; height: 100px"
      :src="resultUrl"
      :zoom-rate="1.2"
      :max-scale="7"
      :min-scale="0.2"
      :preview-src-list="previewList"
      :initial-index="4"
      fit="cover"
    />
          </div>

        </el-tab-pane>

        <el-tab-pane label="模型注册" name="second">模型注册</el-tab-pane>
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
.uploader .image {
  width: 178px;
  height: 178px;
  display: block;
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
import { DArrowRight, View } from "@element-plus/icons-vue";
import type { UploadProps } from "element-plus";
import { ElMessage } from "element-plus";

const activeName = ref("first");
const modelSelected = ref("");

// mode：0表示单张，1表示批量
const rules = ref({
  modelname: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  mode: [{ required: true, message: "必填项不能为空", trigger: "blur" }]
});
const modeParams = reactive({ modelname: "", mode: "" });

const options = [
  {
    value: "model",
    label: "植被提取模型",
    disabled: false
  }
];

// 图像处理
const dialogImageUrl = ref("");
const dialogVisible = ref(false);
const imageUrl = ref("");
const resultUrl= ref("https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg")
const previewList = ref([
  'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg'
])

const uploadSuccess: UploadProps["onSuccess"] = (response, uploadFile) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw!);
};
const uploadError: UploadProps["onError"] = (response) => {
  ElMessage.error("图像上传失败");
};

const beforeUpload: UploadProps["beforeUpload"] = (rawFile) => {
  if (["image/jpeg", "image/png"].indexOf(rawFile.type) === -1) {
    ElMessage.error("请上传jpg、png图像格式");
    return false;
  }
  return true;
};

const handleRemove: UploadProps["onRemove"] = (uploadFile, uploadFiles) => {
  console.log(uploadFile, uploadFiles);
};

const handlePictureCardPreview: UploadProps["onPreview"] = (uploadFile) => {
  dialogImageUrl.value = uploadFile.url!;
  dialogVisible.value = true;
};
</script>
