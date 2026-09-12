<script setup>
import { ref, computed, onMounted } from "vue";
import { UsersIcon } from "@heroicons/vue/24/outline";
import adminApi from "../api/adminApi";
import { useToast } from "../composables/useToast";
import { useAdminAuth } from "../composables/useAdminAuth";
import AdminForm from "../components/admin/AdminForm.vue";
import AdminList from "../components/admin/AdminList.vue";
import AdminDeleteModal from "../components/admin/AdminDeleteModal.vue";
import AdminResetPasswordModal from "../components/admin/AdminResetPasswordModal.vue";
import BaseButton from "../components/ui/BaseButton.vue";
import LoadingSpinner from "../components/ui/LoadingSpinner.vue";
import ConfirmModal from "../components/ui/ConfirmModal.vue";

const { showToast } = useToast();
const { admin: currentAdmin } = useAdminAuth();

const tab = ref("users");

const admins = ref([]);
const loading = ref(false);
const showForm = ref(false);
const selectedAdmin = ref(null);
const deleteTarget = ref(null);
const resetTarget = ref(null);
const deactivateTarget = ref(null);

const currentAdminId = computed(() => currentAdmin.value?.adminId ?? null);

const openForm = (admin = null) => {
  selectedAdmin.value = admin ? { ...admin } : null;
  showForm.value = true;
};

const closeForm = () => {
  showForm.value = false;
  selectedAdmin.value = null;
};

const loadAdmins = async () => {
  loading.value = true;
  try {
    admins.value = await adminApi.getAll();
  } catch (error) {
    console.error("Failed to load admins:", error);
    showToast("Couldn't load admins.", "error");
  } finally {
    loading.value = false;
  }
};

const saveAdmin = async (admin) => {
  const isEditing = !!admin.adminId;
  try {
    if (isEditing) {
      await adminApi.update(admin);
    } else {
      await adminApi.create(admin);
    }
    closeForm();
    await loadAdmins();
    showToast(isEditing ? "Admin updated." : "Admin added.");
  } catch (error) {
    console.error("Failed to save admin:", error);
    showToast(
      isEditing ? "Failed to update admin." : "Failed to add admin.",
      "error",
    );
  }
};

const confirmDelete = (admin) => {
  deleteTarget.value = admin;
};

const deleteAdmin = async () => {
  const admin = deleteTarget.value;
  deleteTarget.value = null;
  if (!admin) return;

  try {
    await adminApi.delete(admin.adminId);
    await loadAdmins();
    showToast("Admin deleted.");
  } catch (error) {
    console.error("Failed to delete admin:", error);
    showToast("Failed to delete admin.", "error");
  }
};

const toggleActive = async (admin) => {
  if (admin.active) {
    deactivateTarget.value = admin;
    return;
  }
  await setAdminActive(admin, true);
};

const confirmDeactivate = async () => {
  const admin = deactivateTarget.value;
  deactivateTarget.value = null;
  if (!admin) return;
  await setAdminActive(admin, false);
};

const setAdminActive = async (admin, active) => {
  try {
    await adminApi.setActive(admin.adminId, active);
    await loadAdmins();
    showToast(active ? "Admin activated." : "Admin deactivated.");
  } catch (error) {
    console.error("Failed to update admin status:", error);
    showToast("Failed to update admin status.", "error");
  }
};

const openResetPassword = (admin) => {
  resetTarget.value = admin;
};

const onPasswordReset = async () => {
  await loadAdmins();
  showToast("Temporary password issued.");
};

onMounted(loadAdmins);
</script>

<template>
  <div class="container-magic pt-28 pb-10">
    <div
      class="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-8"
    >
      <h1 class="text-h2">Admin Dashboard</h1>
      <BaseButton v-if="tab === 'users'" variant="primary" @click="openForm()">
        Add Admin
      </BaseButton>
    </div>

    <div class="flex gap-2.5 mb-8">
      <button
        type="button"
        :class="[
          'rounded-full px-5 py-2.5 text-card font-medium transition-all duration-300 ease-cookie',
          tab === 'customers'
            ? 'bg-chocolate text-cream shadow-soft'
            : 'bg-surface text-chocolate/70 border border-cream-400/60 hover:border-primary hover:text-chocolate',
        ]"
        @click="tab = 'customers'"
      >
        Customers
      </button>
      <button
        type="button"
        :class="[
          'rounded-full px-5 py-2.5 text-card font-medium transition-all duration-300 ease-cookie',
          tab === 'users'
            ? 'bg-chocolate text-cream shadow-soft'
            : 'bg-surface text-chocolate/70 border border-cream-400/60 hover:border-primary hover:text-chocolate',
        ]"
        @click="tab = 'users'"
      >
        Admins
      </button>
    </div>

    <div
      v-if="tab === 'customers'"
      class="card flex flex-col items-center justify-center text-center py-24 px-6"
    >
      <UsersIcon class="h-12 w-12 text-chocolate/30 mb-4" />
      <h3 class="text-xl font-semibold text-chocolate mb-2">Customers</h3>
      <p class="text-chocolate/60">Customer management is coming soon.</p>
    </div>

    <template v-else>
      <div
        v-if="loading"
        class="flex flex-col items-center justify-center py-24"
      >
        <LoadingSpinner label="Loading admins..." />
      </div>

      <div
        v-else-if="admins.length === 0"
        class="flex flex-col items-center justify-center py-24 text-center"
      >
        <p class="text-chocolate/60">No admins found.</p>
      </div>

      <AdminList
        v-else
        :admins="admins"
        :current-admin-id="currentAdminId"
        @edit="openForm"
        @delete="confirmDelete"
        @toggle-active="toggleActive"
        @reset-password="openResetPassword"
      />
    </template>

    <AdminForm
      :open="showForm"
      :admin="selectedAdmin"
      @save="saveAdmin"
      @close="closeForm"
    />

    <AdminDeleteModal
      :open="!!deleteTarget"
      :admin="deleteTarget"
      @confirm="deleteAdmin"
      @close="deleteTarget = null"
    />

    <AdminResetPasswordModal
      :open="!!resetTarget"
      :admin="resetTarget"
      @reset="onPasswordReset"
      @close="resetTarget = null"
    />

    <ConfirmModal
      :open="!!deactivateTarget"
      title="Deactivate admin?"
      :message="`${deactivateTarget?.firstName} ${deactivateTarget?.lastName} won't be able to log in until reactivated.`"
      confirm-text="Deactivate"
      variant="danger"
      @confirm="confirmDeactivate"
      @close="deactivateTarget = null"
    />
  </div>
</template>
