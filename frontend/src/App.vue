<template>
  <div class="min-h-screen flex flex-col bg-background">
    <TheNavbar v-if="!route.meta.hideChrome" />

    <main class="flex-1">
      <RouterView v-slot="{ Component, route: currentRoute }">
        <Transition name="page" mode="out-in">
          <component :is="Component" :key="currentRoute.fullPath" />
        </Transition>
      </RouterView>
    </main>

    <TheFooter v-if="!route.meta.hideChrome" />
    <ToastContainer />
  </div>
</template>

<script setup>
import { RouterView, useRoute } from "vue-router";
import TheNavbar from "./layout/TheNavbar.vue";
import TheFooter from "./layout/TheFooter.vue";
import ToastContainer from "./components/ui/ToastContainer.vue";

const route = useRoute();
</script>

<style>
#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

main {
  flex: 1;
}
</style>
