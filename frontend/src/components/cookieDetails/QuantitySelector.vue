<template>
  <div>
    <p
        class="text-sm font-semibold uppercase tracking-wide text-cream-100/50 mb-2"
    >
      Quantity
    </p>
    <div
        class="inline-flex items-center border border-primary rounded-full overflow-hidden"
    >
      <button
          type="button"
          class="px-5 py-3 text-cream-50 hover:bg-primary/10 transition-colors disabled:opacity-40 disabled:cursor-not-allowed"
          :disabled="modelValue <= 1"
          @click="updateQuantity(Math.max(1, modelValue - 1))"
      >
        -
      </button>

      <span class="px-8 text-cream-50 font-semibold">
        {{ modelValue }}
      </span>

      <button
          type="button"
          class="px-5 py-3 text-cream-50 hover:bg-primary/10 transition-colors"
          @click="updateQuantity(modelValue + 1)"
      >
        +
      </button>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  modelValue: {
    type: Number,
    default: 1,
  },
});

const emit = defineEmits(["update:modelValue"]);

const updateQuantity = (val) => {
  emit("update:modelValue", val);
};
</script>