<script setup>
/**
 * ToastContainer — mount once (see App.vue). Renders whatever is pushed
 * via useToast().showToast(message, type).
 */
import { CheckCircleIcon, ExclamationCircleIcon, XMarkIcon } from "@heroicons/vue/24/solid";
import { useToast } from "../../composables/useToast";

const { toasts, removeToast } = useToast();
</script>

<template>
  <Teleport to="body">
    <div class="fixed top-6 right-6 z-[200] flex flex-col gap-3 w-[calc(100%-3rem)] max-w-sm">
      <TransitionGroup name="toast">
        <div
          v-for="toast in toasts"
          :key="toast.id"
          class="flex items-center gap-3 rounded-2xl px-5 py-4 shadow-soft-lg border"
          :class="
            toast.type === 'error'
              ? 'bg-red-50 border-red-200 text-red-800'
              : 'bg-surface border-cream-300 text-chocolate'
          "
        >
          <ExclamationCircleIcon
            v-if="toast.type === 'error'"
            class="h-5 w-5 shrink-0 text-red-500"
          />
          <CheckCircleIcon v-else class="h-5 w-5 shrink-0 text-primary-600" />

          <p class="text-sm font-medium flex-1">{{ toast.message }}</p>

          <button
            type="button"
            class="text-current opacity-40 hover:opacity-100 transition-opacity"
            aria-label="Dismiss"
            @click="removeToast(toast.id)"
          >
            <XMarkIcon class="h-4 w-4" />
          </button>
        </div>
      </TransitionGroup>
    </div>
  </Teleport>
</template>

<style scoped>
.toast-enter-active,
.toast-leave-active {
  transition:
    opacity 0.3s ease,
    transform 0.3s ease;
}
.toast-enter-from,
.toast-leave-to {
  opacity: 0;
  transform: translateX(40px);
}
.toast-leave-active {
  position: absolute;
  width: 100%;
}
</style>
