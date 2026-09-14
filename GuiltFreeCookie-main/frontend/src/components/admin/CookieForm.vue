<script setup>
import { ref, computed, watch } from "vue";
import { XMarkIcon } from "@heroicons/vue/24/solid";
import cookieApi from "../../api/cookieApi";
import { COOKIE_CATEGORIES, formatCategoryLabel } from "../../constants/cookieCategories";
import BaseModal from "../ui/BaseModal.vue";
import BaseInput from "../ui/BaseInput.vue";
import BaseButton from "../ui/BaseButton.vue";
import ConfirmModal from "../ui/ConfirmModal.vue";

const props = defineProps({
  open: {
    type: Boolean,
    default: false,
  },
  cookie: {
    type: Object,
    default: null,
  },
});

const emit = defineEmits(["save", "close"]);

const categoryOptions = COOKIE_CATEGORIES.map((id) => ({
  value: id,
  label: formatCategoryLabel(id),
}));

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
const imageFile = ref(null);
const imagePreview = ref(null);
const imageRemoved = ref(false);
const isEditing = computed(() => !!form.value.cookieId);
const showConfirm = ref(false);

// 2. Reset form state every time the modal opens
watch(
  () => props.open,
  (isOpen) => {
    if (!isOpen) return;
    form.value = props.cookie ? { ...props.cookie } : { ...defaultForm };
    imageFile.value = null;
    imageRemoved.value = false;
    imagePreview.value = props.cookie ? cookieApi.getImageUrl(props.cookie.image) : null;
    showConfirm.value = false;
  },
  { immediate: true },
);

const onImageChange = (event) => {
  const file = event.target.files[0];
  if (!file) return;
  imageFile.value = file;
  imageRemoved.value = false;
  imagePreview.value = URL.createObjectURL(file);
};

const removeImage = () => {
  imageFile.value = null;
  imagePreview.value = null;
  imageRemoved.value = true;
};

const canSubmit = computed(
  () =>
    form.value.description &&
    form.value.category &&
    form.value.price > 0 &&
    (imageFile.value || (isEditing.value && !imageRemoved.value)),
);

const openConfirm = () => {
  showConfirm.value = true;
};

const confirmSubmit = () => {
  showConfirm.value = false;
  emit("save", { cookie: form.value, imageFile: imageFile.value });
};
</script>

<template>
  <BaseModal :open="open" :title="cookie ? 'Edit Cookie' : 'Add Cookie'" @close="emit('close')">
    <div class="space-y-4">
      <BaseInput v-model="form.description" label="Description" placeholder="e.g. Chocolate Stevia Cookie" required />

      <BaseInput v-model="form.ingredients" label="Ingredients" placeholder="e.g. Almond flour, Cocoa powder" required />

      <BaseInput v-model="form.allergies" label="Allergies" placeholder="e.g. Tree Nuts" required />

      <BaseInput v-model="form.price" type="number" label="Price (R)" required />

      <BaseInput
        v-model="form.category"
        type="select"
        label="Category"
        :options="[{ value: '', label: 'Select Category' }, ...categoryOptions]"
        required
      />

      <div>
        <label class="block text-card font-medium mb-2 text-chocolate/80">
          Image
          <span class="text-primary">*</span>
        </label>

        <div v-if="imagePreview" class="relative w-28 h-28 mb-3">
          <img
            :src="imagePreview"
            alt="Cookie preview"
            class="w-full h-full object-cover rounded-2xl"
          />
          <button
            type="button"
            class="absolute -top-2 -right-2 h-7 w-7 flex items-center justify-center rounded-full bg-chocolate text-cream shadow-soft hover:bg-chocolate-600 transition-colors"
            aria-label="Remove image"
            @click="removeImage"
          >
            <XMarkIcon class="h-4 w-4" />
          </button>
        </div>

        <input
          v-else
          type="file"
          accept="image/*"
          class="field"
          @change="onImageChange"
        />

        <p v-if="isEditing && !imagePreview && !imageRemoved" class="mt-2 text-sm text-chocolate/50">
          Current image kept unless you upload a new one.
        </p>
      </div>
    </div>

    <div class="flex justify-end gap-3 mt-8">
      <BaseButton variant="outline" size="sm" @click="emit('close')">Cancel</BaseButton>
      <BaseButton variant="primary" size="sm" :disabled="!canSubmit" @click="openConfirm">
        Save
      </BaseButton>
    </div>

    <ConfirmModal
      :open="showConfirm"
      :title="isEditing ? 'Save changes?' : 'Add cookie?'"
      :message="
        isEditing
          ? `Save changes to ${form.description || 'this cookie'}?`
          : `Add ${form.description || 'this cookie'} to the menu?`
      "
      confirm-text="Save"
      @confirm="confirmSubmit"
      @close="showConfirm = false"
    />
  </BaseModal>
</template>
