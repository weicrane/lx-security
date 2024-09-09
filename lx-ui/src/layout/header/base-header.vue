<script lang="ts">
import logoicon from "@/assets/images/small-logo.png";
import logo from "@/assets/images/logo.png";
import { EMitt, ESidebarLayoutEnum, EThemeSetting } from "@/constants/enum";
import emits from "@/utils/emits";
import { getThemeConfigCacheByKey } from "@/utils/theme";
import { defineComponent, reactive } from "vue";
import { useAppStore } from "@/store";
import BaseSidebar from "../sidebar/base-sidebar.vue";
import Breadcrumb from "./breadcrumb.vue";
import CollapseSidebarBtn from "./collapse-sidebar-btn.vue";
import Expand from "./expand.vue";
import HeaderMixNavMenus from "./header-mix-nav-menus.vue";
import Logo from "./logo.vue";
import "@/assets/css/header.less";

/**
 * 顶部主区域
 */
export default defineComponent({
  name: "Header",
  components: { BaseSidebar, Breadcrumb, CollapseSidebarBtn, Expand, HeaderMixNavMenus, Logo },
  setup() {
    const store = useAppStore();
    const state = reactive({
      sidebarLayout: getThemeConfigCacheByKey(EThemeSetting.NavLayout)
    });
    emits.on(EMitt.OnSetNavLayout, (vl) => {
      state.sidebarLayout = vl;
    });
    const onRefresh = () => {
      emits.emit(EMitt.OnReloadTabPage);
    };
    return { store, state, onRefresh, logoicon, logo, ESidebarLayoutEnum };
  }
});
</script>
<template>
  <!-- <div class="ruoergai-header-logo">
    <img class="ruoergai-header-logo-icon" :src="logoicon">
    <img class="ruoergai-header-logo-main" :src="logo">
  </div> -->
  <div class="rr-header-ctx">
    <div class="rr-header-right">
      <div class="rr-header-right-left">
        <div class="rr-header-right-items rr-header-action" :style="`display:${state.sidebarLayout === ESidebarLayoutEnum.Top ? 'none' : ''}`">
          <!-- <collapse-sidebar-btn></collapse-sidebar-btn> -->
          <div >
            <img class="ruoergai-header-logo-icon" :src="logoicon">
             <img class="ruoergai-header-logo-main" :src="logo">
          </div>
        </div>
        <div class="rr-header-right-left-br ele-scrollbar-hide hidden-xs-only">
          <base-sidebar v-if="state.sidebarLayout === ESidebarLayoutEnum.Top" mode="horizontal" :router="true"></base-sidebar>
          <header-mix-nav-menus v-else-if="state.sidebarLayout === ESidebarLayoutEnum.Mix"></header-mix-nav-menus>
          <breadcrumb v-else></breadcrumb>
        </div>
      </div>
      <div style="flex-shrink: 0">
        <expand :userName="store.state.user.username"></expand>
      </div>
    </div>
  </div>
</template>
<style>
.ruoergai-header-logo{
  display: flex ;
  text-align: center;
}
.ruoergai-header-logo-icon{
  margin-top: 10px;
  border-bottom: 5px solid transparent; /* 创建透明边框以实现透明边距 */
}
.ruoergai-header-logo-main{
  margin-left: 10px;
}
</style>
