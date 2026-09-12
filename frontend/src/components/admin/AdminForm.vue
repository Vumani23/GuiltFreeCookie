<script setup>
import { ref, computed, watch } from "vue";
import BaseModal from "../ui/BaseModal.vue";
import BaseInput from "../ui/BaseInput.vue";
import BaseButton from "../ui/BaseButton.vue";
import ConfirmModal from "../ui/ConfirmModal.vue";

const props = defineProps({
  open: {
    type: Boolean,
    default: false,
  },
  admin: {
    type: Object,
    default: null,
  },
});

const emit = defineEmits(["save", "close"]);

const defaultForm = {
  adminId: null,
  firstName: "",
  lastName: "",
  email: "",
  password: "",
  active: true,
};

const form = ref({ ...defaultForm });
const isEditing = computed(() => !!form.value.adminId);
const showConfirm = ref(false);

watch(
  () => props.open,
  (isOpen) => {
    if (!isOpen) return;
    form.value = props.admin ? { ...props.admin, password: "" } : { ...defaultForm };
    showConfirm.value = false;
  },
  { immediate: true },
);

const canSubmit = computed(
  () =>
    form.value.firstName &&
    form.value.lastName &&
    form.value.email &&
    (isEditing.value || form.value.password),
);

const openConfirm = () => {
  showConfirm.value = true;
};

const confirmSubmit = () => {
  showConfirm.value = false;
  emit("save", form.value);
};
</script>

<template>
  <BaseModal :open="open" :title="admin ? 'Edit Admin' : 'Add Admin'" @close="emit('close')">
    <div class="space-y-4">
      <BaseInput v-model="form.firstName" label="First Name" placeholder="Jane" required />
      <BaseInput v-model="form.lastName" label="Last Name" placeholder="Doe" required />
      <BaseInput
        v-model="form.email"
        type="email"
        label="Email"
        placeholder="jane@guiltfreecookie.com"
        required
      />

      <BaseInput
        v-if="!isEditing"
        v-model="form.password"
        type="password"
        label="Password"
        placeholder="••••••••"
        required
      />
      <p v-else class="text-sm text-chocolate/50">
        Use "Reset Password" from the list to issue a new temporary password.
      </p>
    </div>

    <div class="flex justify-end gap-3 mt-8">
      <BaseButton variant="outline" size="sm" @click="emit('close')">Cancel</BaseButton>
      <BaseButton variant="primary" size="sm" :disabled="!canSubmit" @click="openConfirm">
        Save
      </BaseButton>
    </div>

    <ConfirmModal
      :open="showConfirm"
      :title="isEditing ? 'Save changes?' : 'Add admin?'"
      :message="
        isEditing
          ? `Save changes to ${form.firstName} ${form.lastName}?`
          : `Add ${form.firstName} ${form.lastName} as a new admin?`
      "
      confirm-text="Save"
      @confirm="confirmSubmit"
      @close="showConfirm = false"
    />
  </BaseModal>
</template>
