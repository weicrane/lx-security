<template>
  <el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()" label-width="120px">
          <el-form-item label="用户ID" prop="userId">
        <el-input v-model="dataForm.userId" placeholder="用户ID"></el-input>
      </el-form-item>
          <el-form-item label="token" prop="token">
        <el-input v-model="dataForm.token" placeholder="token"></el-input>
      </el-form-item>
          <el-form-item label="过期时间" prop="expireDate">
        <el-input v-model="dataForm.expireDate" placeholder="过期时间"></el-input>
      </el-form-item>
        </el-form>
    <template #footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmitHandle()">确定</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import baseService from "@/service/baseService";
import { ElMessage } from "element-plus";
const emit = defineEmits(["refreshDataList"]);

const visible = ref(false);
const dataFormRef = ref();

const dataForm = reactive({
  id: '',  userId: '',  token: '',  expireDate: '',  updateDate: ''});

const rules = ref({
          userId: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          token: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          expireDate: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
    });

const init = (id?: number) => {
  visible.value = true;
  dataForm.id = "";

  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields();
  }

  if (id) {
    getInfo(id);
  }
};

// 获取信息
const getInfo = (id: number) => {
  baseService.get("/wxapp/tbtoken/" + id).then((res) => {
    Object.assign(dataForm, res.data);
  });
};

// 表单提交
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    (!dataForm.id ? baseService.post : baseService.put)("/wxapp/tbtoken", dataForm).then((res) => {
      ElMessage.success({
        message: '成功',
        duration: 500,
        onClose: () => {
          visible.value = false;
          emit("refreshDataList");
        }
      });
    });
  });
};

defineExpose({
  init
});
</script>
