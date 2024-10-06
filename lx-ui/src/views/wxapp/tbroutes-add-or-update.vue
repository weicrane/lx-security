<template>
  <el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()" label-width="120px">
          <el-form-item label="" prop="routeCode">
        <el-input v-model="dataForm.routeCode" placeholder=""></el-input>
      </el-form-item>
          <el-form-item label="" prop="name">
        <el-input v-model="dataForm.name" placeholder=""></el-input>
      </el-form-item>
          <el-form-item label="" prop="description">
        <el-input v-model="dataForm.description" placeholder=""></el-input>
      </el-form-item>
          <el-form-item label="" prop="price">
        <el-input v-model="dataForm.price" placeholder=""></el-input>
      </el-form-item>
          <el-form-item label="" prop="duration">
        <el-input v-model="dataForm.duration" placeholder=""></el-input>
      </el-form-item>
          <el-form-item label="" prop="createdAt">
        <el-input v-model="dataForm.createdAt" placeholder=""></el-input>
      </el-form-item>
          <el-form-item label="" prop="updatedAt">
        <el-input v-model="dataForm.updatedAt" placeholder=""></el-input>
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
  id: '',  routeCode: '',  name: '',  description: '',  price: '',  duration: '',  createdAt: '',  updatedAt: ''});

const rules = ref({
          routeCode: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          name: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          description: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          price: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          duration: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          createdAt: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          updatedAt: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ]
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
  baseService.get("/wxapp/tbroutes/" + id).then((res) => {
    Object.assign(dataForm, res.data);
  });
};

// 表单提交
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    (!dataForm.id ? baseService.post : baseService.put)("/wxapp/tbroutes", dataForm).then((res) => {
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
