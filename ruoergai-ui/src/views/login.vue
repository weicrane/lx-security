<template>
  <div class="rr-login">
    <div class="rr-login-wrap">
      <div class="rr-login-right">
        <div class="rr-login-right-main">
          <el-image src="/src/assets/images/title.png" style="margin: 30px 0"></el-image>
          <el-form ref="formRef" label-width="80px" :status-icon="true" :model="login" :rules="rules" @keyup.enter="onLogin">
            <el-form-item label-width="0" prop="username">
              <el-input v-model="login.username" placeholder="请输入登录用户" prefix-icon="user" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label-width="0" prop="password">
              <el-input placeholder="请输入登录密码" v-model="login.password" prefix-icon="lock" autocomplete="off" show-password></el-input>
            </el-form-item>
            <el-form-item label-width="0" prop="captcha">
              <el-space class="rr-login-right-main-code">
                <el-input v-model="login.captcha" placeholder="验证码" prefix-icon="first-aid-kit"></el-input>
                <img style="vertical-align: middle; height: 40px; cursor: pointer" :src="state.captchaUrl" @click="onRefreshCode" alt="" />
              </el-space>
            </el-form-item>
            <el-form-item label-width="0">
              <el-button type="primary" size="small" round :disabled="state.loading" @click="onLogin" class="rr-login-right-main-btn" color="#2975FF"> 登录 </el-button>
            </el-form-item>
            <el-form-item label-width="0" class="options">
              <el-button link type="primary" size="small" :disabled="state.loading" @click="resetPasswordDialog = true;" color="#AAAAAA"> 重置密码 </el-button>
              <el-text class="mx-1" type="info"> | </el-text>
              <el-button link type="primary" size="small" :disabled="state.loading" @click="registDialog = true" color="#AAAAAA"> 立即注册 </el-button>
            </el-form-item>
          </el-form>
        </div>
        <!-- 重置密码弹框 -->
        <el-dialog v-model="resetPasswordDialog" title="重置密码" width="500" align-center>
          <el-form ref="resetForm" label-width="auto" :rules="resetRules" :model="reset">
            <el-form-item prop="username" label="用户名">
              <el-input v-model="reset.username" placeholder="请输入登录用户" prefix-icon="user" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item prop="password" label="原密码">
              <el-input placeholder="请输入原密码" v-model="reset.password" prefix-icon="lock" autocomplete="off" show-password></el-input>
            </el-form-item>
            <el-form-item prop="password" label="新密码">
              <el-input placeholder="请输入新密码" v-model="reset.newpassword" prefix-icon="lock" autocomplete="off" show-password></el-input>
            </el-form-item>
          </el-form>
          <template #footer>
            <div class="dialog-footer">
              <el-button @click="resetPasswordDialog = false">取消</el-button>
              <el-button type="primary" @click="onReset"> 确认 </el-button>
            </div>
          </template>
        </el-dialog>
                <!-- 注册用户弹框 -->
                <el-dialog v-model="registDialog" title="注册" width="500" align-center>
          <el-form ref="registForm" label-width="auto" :rules="registRules" :model="regist">
            <el-form-item prop="username" label="用户名">
              <el-input v-model="regist.username" placeholder="登录用户名" prefix-icon="user" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item prop="password" label="密码">
              <el-input placeholder="请输入密码" v-model="regist.password" prefix-icon="lock" autocomplete="off" show-password></el-input>
            </el-form-item>
            <el-form-item prop="mobile" label="手机号">
              <el-input placeholder="请输入手机号" v-model="regist.mobile" prefix-icon="lock" autocomplete="off" ></el-input>
            </el-form-item>
            <el-form-item prop="realname" label="真实姓名">
              <el-input placeholder="请输入真实姓名" v-model="regist.realname" prefix-icon="lock" autocomplete="off" ></el-input>
            </el-form-item>
          </el-form>
          <template #footer>
            <div class="dialog-footer">
              <el-button @click="registDialog = false">取消</el-button>
              <el-button type="primary" @click="onRegist"> 确认 </el-button>
            </div>
          </template>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref } from "vue";
import { CacheToken } from "@/constants/cacheKey";
import baseService from "@/service/baseService";
import { setCache } from "@/utils/cache";
import { ElMessage } from "element-plus";
import { getUuid } from "@/utils/utils";
import app from "@/constants/app";
import { useAppStore } from "@/store";
import { useRouter } from "vue-router";

const store = useAppStore();
const router = useRouter();

const state = reactive({
  captchaUrl: "",
  loading: false,
  year: new Date().getFullYear()
});

const login = reactive({ username: "", password: "", captcha: "", uuid: "" });
const reset = reactive({ username: "", password: "", newpassword: "" });
const regist = reactive({ username: "", password: "",mobile:"", realname: "" });

onMounted(() => {
  //清理数据
  store.logout();
  getCaptchaUrl();
});
const formRef = ref();
const resetForm = ref();
const registForm = ref();

const rules = ref({
  username: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  password: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  captcha: [{ required: true, message: "必填项不能为空", trigger: "blur" }]
});
const resetRules = ref({
  username: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  password: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  newPassword: [{ required: true, message: "必填项不能为空", trigger: "blur" }]
});
const registRules = ref({
  username: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  password: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  mobile: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  realname: [{ required: true, message: "必填项不能为空", trigger: "blur" }]
});

const resetPasswordDialog = ref();
const registDialog = ref();


const getCaptchaUrl = () => {
  login.uuid = getUuid();
  state.captchaUrl = `${app.api}/captcha?uuid=${login.uuid}`;
};

const onRefreshCode = () => {
  getCaptchaUrl();
};

const onLogin = () => {
  formRef.value.validate((valid: boolean) => {
    if (valid) {
      state.loading = true;
      baseService
        .post("/login", login)
        .then((res) => {
          state.loading = false;
          if (res.code === 0) {
            setCache(CacheToken, res.data, true);
            ElMessage.success("登录成功");
            router.push("/");
          } else {
            ElMessage.error(res.msg);
          }
        })
        .catch(() => {
          state.loading = false;
          onRefreshCode();
        });
    }
  });
};
// 重置密码
const onReset = () => {
  resetForm.value.validate((valid: boolean) => {
    if (valid) {
      state.loading = true;
      baseService
        .post("/reset", reset)
        .then((res) => {
          state.loading = false;
          if (res.code === 0) {
            ElMessage.success("重置成功");
            resetPasswordDialog.value = false;
          } else {
            ElMessage.error(res.msg);
          }
        })
        .catch(() => {
          state.loading = false;
        });
    }
  });
};
// 注册
const onRegist = () => {
  registForm.value.validate((valid: boolean) => {
    if (valid) {
      state.loading = true;
      baseService
        .post("/regist", regist)
        .then((res) => {
          state.loading = false;
          if (res.code === 0) {
            ElMessage.success("注册成功");
            registDialog.value = false;
          } else {
            ElMessage.error(res.msg);
          }
        })
        .catch(() => {
          state.loading = false;
        });
    }
  });
};
</script>

<style lang="less" scoped>
@import url("@/assets/theme/base.less");
.rr-login {
  width: 100vw;
  height: 100vh;
  background-image: url("../../src/assets/images/u0.jpg");
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  @media only screen and (max-width: 992px) {
    .rr-login-wrap {
      width: 96% !important;
    }
    .rr-login-right {
      width: 100% !important;
    }
  }

  &-wrap {
    margin: 0 auto;
    width: 600px;
    box-shadow: -4px 5px 10px rgba(0, 0, 0, 0.4);
    animation-duration: 1s;
    animation-fill-mode: both;
    border-radius: 5px;
    overflow: hidden;
  }

  &-right {
    border-left: none;
    color: #fff;
    background-color: #fff;
    width: 100%;
    float: left;

    &-main {
      margin: 0 auto;
      width: 65%;

      &-lang .iconfont {
        font-size: 20px;
        color: #606266;
        font-weight: 800;
        width: 20px;
        height: 20px;
      }

      .el-input__inner {
        border-width: 0;
        border-radius: 0;
        border-bottom: 1px solid #dcdfe6;
      }

      &-code {
        width: 100%;
        .el-space__item:first-child {
          flex: 1;
        }
      }
      &-btn {
        width: 100%;
        height: 45px;
        font-size: 18px !important;
        letter-spacing: 2px;
        font-weight: 300 !important;
        cursor: pointer;
        margin-top: 30px;
        font-family: neo, sans-serif;
        transition: 0.25s;
      }
    }
  }

  &-left,
  &-right {
    position: relative;
    min-height: 500px;
    align-items: center;
    display: flex;
  }

  @keyframes animate-down {
    0%,
    60%,
    75%,
    90%,
    to {
      animation-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
    }
    0% {
      opacity: 0;
      transform: translate3d(0, -3000px, 0);
    }
    60% {
      opacity: 1;
      transform: translate3d(0, 25px, 0);
    }
    75% {
      transform: translate3d(0, -10px, 0);
    }
    90% {
      transform: translate3d(0, 5px, 0);
    }
    to {
      transform: none;
    }
  }

  .animate-down {
    animation-name: animate-down;
  }

  .options :v-deep(.el-form-item__content) {
    justify-content: center !important;
  }
}
</style>
