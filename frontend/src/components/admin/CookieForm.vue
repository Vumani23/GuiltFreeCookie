<script setup>
import { ref, watch } from "vue";

const props = defineProps({
  cookie: {
    type: Object,
    default: null,
  },
});

const emit = defineEmits(["save", "close"]);

// 1. Default form state template
const defaultForm = {
  cookieId: null,
  category: "",
  description: "",
  ingredients: "",
  allergies: "",
  price: 0,
};

const form = ref({ ...defaultForm });

// 2. Simple watch with object spread
watch(
  () => props.cookie,
  (cookie) => {
    form.value = cookie ? { ...cookie } : { ...defaultForm };
  },
  { immediate: true },
);

const submit = () => {
  emit("save", form.value);
};
</script>

<template>
  <div class="fixed inset-0 bg-black/50 flex justify-center items-center z-50">
    <div class="bg-white p-6 rounded-xl w-[500px]">
      <h2 class="text-xl font-bold mb-4">
        {{ cookie ? "Edit Cookie" : "Add Cookie" }}
      </h2>

      <input
        v-model="form.description"
        placeholder="Description"
        class="border w-full mb-3 p-2 rounded"
      />

      <input
        v-model="form.ingredients"
        placeholder="Ingredients"
        class="border w-full mb-3 p-2 rounded"
      />

      <input
        v-model="form.allergies"
        placeholder="Allergies"
        class="border w-full mb-3 p-2 rounded"
      />

      <input
        v-model.number="form.price"
        type="number"
        placeholder="Price"
        class="border w-full mb-3 p-2 rounded"
      />

      <select v-model="form.category" class="border w-full mb-4 p-2 rounded">
        <option value="" disabled>Select Category</option>
        <option>LOW_SUGAR</option>
        <option>VEGAN</option>
        <option>HIGH_PROTEIN</option>
        <option>GLUTEN_FREE</option>
        <option>CLASSIC</option>
      </select>

      <div class="flex justify-end gap-3">
        <button type="button" @click="emit('close')">Cancel</button>
        <button
          class="bg-primary text-white px-4 py-2 rounded disabled:opacity-50"
          :disabled="!form.description || !form.category || form.price <= 0"
          @click="submit"
        >
          Save
        </button>
      </div>
    </div>
  </div>
</template>
