<script setup>
import { ref, watch } from "vue";
import adminApi from "../api/adminApi";
import { useAdminAuth } from "../composables/useAdminAuth";
import { useToast } from "../composables/useToast";
import BaseInput from "../components/ui/BaseInput.vue";
import BaseButton from "../components/ui/BaseButton.vue";
import ConfirmModal from "../components/ui/ConfirmModal.vue";

const { admin, updateAdmin } = useAdminAuth();
const { showToast } = useToast();

const profileForm = ref({ firstName: "", lastName: "", email: "" });
const savingProfile = ref(false);
const showProfileConfirm = ref(false);

const passwordForm = ref({ currentPassword: "", newPassword: "", confirmPassword: "" });
const passwordError = ref("");
const changingPassword = ref(false);
const showPasswordConfirm = ref(false);

watch(
  admin,
  (value) => {
    if (!value) return;
    profileForm.value = {
      firstName: value.firstName,
      lastName: value.lastName,
      email: value.email,
    };
  },
  { immediate: true },
);

const confirmSaveProfile = async () => {
  showProfileConfirm.value = false;
  savingProfile.value = true;
  try {
    const updated = await adminApi.update({ ...admin.value, ...profileForm.value });
    updateAdmin(updated);
    showToast("Profile updated.");
  } catch (error) {
    console.error("Failed to update profile:", error);
    showToast("Failed to update profile.", "error");
  } finally {
    savingProfile.value = false;
  }
};

const canChangePassword = () =>
  passwordForm.value.currentPassword &&
  passwordForm.value.newPassword &&
  passwordForm.value.newPassword === passwordForm.value.confirmPassword;

const openPasswordConfirm = () => {
  passwordError.value = "";

  if (passwordForm.value.newPassword !== passwordForm.value.confirmPassword) {
    passwordError.value = "New passwords don't match.";
    return;
  }

  showPasswordConfirm.value = true;
};

const confirmChangePassword = async () => {
  showPasswordConfirm.value = false;
  changingPassword.value = true;
  try {
    const updated = await adminApi.changePassword(
      admin.value.adminId,
      passwordForm.value.currentPassword,
      passwordForm.value.newPassword,
    );
    updateAdmin(updated);
    passwordForm.value = { currentPassword: "", newPassword: "", confirmPassword: "" };
    showToast("Password changed.");
  } catch (error) {
    passwordError.value =
      error.response?.status === 401
        ? "Current password is incorrect."
        : "Failed to change password.";
  } finally {
    changingPassword.value = false;
  }
};
</script>

<template>
  <div class="container-magic pt-28 pb-10 max-w-2xl">
    <h1 class="text-h2 mb-8">My Profile</h1>

    <div class="card p-8 mb-8">
      <h2 class="text-xl font-semibold text-chocolate mb-6">Profile Details</h2>
      <div class="space-y-4">
        <div class="grid sm:grid-cols-2 gap-4">
          <BaseInput v-model="profileForm.firstName" label="First Name" required />
          <BaseInput v-model="profileForm.lastName" label="Last Name" required />
        </div>
        <BaseInput v-model="profileForm.email" type="email" label="Email" required />
      </div>
      <div class="flex justify-end mt-6">
        <BaseButton
          variant="primary"
          size="sm"
          :loading="savingProfile"
          :disabled="!profileForm.firstName || !profileForm.lastName || !profileForm.email"
          @click="showProfileConfirm = true"
        >
          Save Changes
        </BaseButton>
      </div>
    </div>

    <div class="card p-8">
      <h2 class="text-xl font-semibold text-chocolate mb-6">Change Password</h2>
      <div class="space-y-4">
        <BaseInput
          v-model="passwordForm.currentPassword"
          type="password"
          label="Current Password"
          required
        />
        <BaseInput
          v-model="passwordForm.newPassword"
          type="password"
          label="New Password"
          required
        />
        <BaseInput
          v-model="passwordForm.confirmPassword"
          type="password"
          label="Confirm New Password"
          required
        />

        <p v-if="passwordError" class="text-sm text-red-600">{{ passwordError }}</p>
      </div>
      <div class="flex justify-end mt-6">
        <BaseButton
          variant="primary"
          size="sm"
          :loading="changingPassword"
          :disabled="!canChangePassword()"
          @click="openPasswordConfirm"
        >
          Change Password
        </BaseButton>
      </div>
    </div>

    <ConfirmModal
      :open="showProfileConfirm"
      title="Save changes?"
      message="Save changes to your profile details?"
      confirm-text="Save"
      :loading="savingProfile"
      @confirm="confirmSaveProfile"
      @close="showProfileConfirm = false"
    />

    <ConfirmModal
      :open="showPasswordConfirm"
      title="Change password?"
      message="You'll need your new password the next time you log in."
      confirm-text="Change Password"
      variant="danger"
      :loading="changingPassword"
      @confirm="confirmChangePassword"
      @close="showPasswordConfirm = false"
    />
  </div>
</template>
