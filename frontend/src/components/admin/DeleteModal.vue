<script setup>
import BaseModal from "../ui/BaseModal.vue";
import BaseButton from "../ui/BaseButton.vue";

defineProps({
  open: {
    type: Boolean,
    default: false,
  },
  cookie: {
    type: Object,
    default: null,
  },
});

const emit = defineEmits(["confirm", "close"]);
</script>

<template>
  <BaseModal :open="open" title="Delete cookie?" size="sm" @close="emit('close')">
    <p class="text-chocolate/70 mb-6">
      Are you sure you want to delete
      <span class="font-semibold text-chocolate">{{ cookie?.description }}</span>? This
      can't be undone.
    </p>

    <div class="flex justify-end gap-3">
      <BaseButton variant="outline" size="sm" @click="emit('close')">Cancel</BaseButton>
      <button
        type="button"
        class="btn text-sm px-5 py-2.5 bg-red-600 text-white hover:bg-red-700 hover:-translate-y-0.5 active:translate-y-0"
        @click="emit('confirm')"
      >
        Delete
      </button>
    </div>
  </BaseModal>
</template>
