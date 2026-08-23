<script setup>
import { ref, computed, onMounted } from "vue";
import cookieApi from "../api/cookieApi";
import { COOKIE_CATEGORIES, formatCategoryLabel } from "../constants/cookieCategories";
import { useToast } from "../composables/useToast";
import CookieForm from "../components/admin/CookieForm.vue";
import CookieGrid from "../components/admin/CookieGrid.vue";
import DeleteModal from "../components/admin/DeleteModal.vue";
import SearchBar from "../components/ui/SearchBar.vue";
import BaseInput from "../components/ui/BaseInput.vue";
import BaseButton from "../components/ui/BaseButton.vue";
import LoadingSpinner from "../components/ui/LoadingSpinner.vue";
import ConfirmModal from "../components/ui/ConfirmModal.vue";

const { showToast } = useToast();

const cookies = ref([]);
const loading = ref(false);
const showForm = ref(false);
const selectedCookie = ref(null);
const deleteTarget = ref(null);
const archiveTarget = ref(null);

const search = ref("");
const category = ref("all");

const categories = computed(() => [
  { id: "all", label: "All" },
  ...COOKIE_CATEGORIES.map((id) => ({ id, label: formatCategoryLabel(id) })),
]);

const counts = computed(() => {
  const result = { all: cookies.value.length };
  cookies.value.forEach((c) => {
    result[c.category] = (result[c.category] || 0) + 1;
  });
  return result;
});

const categoryOptions = computed(() =>
  categories.value.map((c) => ({
    value: c.id,
    label: counts.value[c.id] !== undefined ? `${c.label} (${counts.value[c.id]})` : c.label,
  })),
);

const filteredCookies = computed(() => {
  let result = [...cookies.value];

  if (search.value) {
    const term = search.value.toLowerCase();
    result = result.filter(
      (c) =>
        c.description?.toLowerCase().includes(term) ||
        c.ingredients?.toLowerCase().includes(term),
    );
  }

  if (category.value !== "all") {
    result = result.filter((c) => c.category === category.value);
  }

  return result;
});

// Helpers for Modal State
const openForm = (cookie = null) => {
  selectedCookie.value = cookie ? { ...cookie } : null;
  showForm.value = true;
};

const closeForm = () => {
  showForm.value = false;
  selectedCookie.value = null;
};

// API Actions
const loadCookies = async () => {
  loading.value = true;
  try {
    cookies.value = await cookieApi.getAllForAdmin();
  } catch (error) {
    console.error("Failed to load cookies:", error);
    showToast("Couldn't load cookies.", "error");
  } finally {
    loading.value = false;
  }
};

const saveCookie = async ({ cookie, imageFile }) => {
  const isEditing = !!cookie.cookieId;
  try {
    await cookieApi.save(cookie, imageFile);
    closeForm();
    await loadCookies();
    showToast(isEditing ? "Cookie updated." : "Cookie added.");
  } catch (error) {
    console.error("Failed to save cookie:", error);
    showToast(isEditing ? "Failed to update cookie." : "Failed to add cookie.", "error");
  }
};

const confirmDelete = (cookie) => {
  deleteTarget.value = cookie;
};

const deleteCookie = async () => {
  const cookie = deleteTarget.value;
  deleteTarget.value = null;
  if (!cookie) return;

  try {
    await cookieApi.delete(cookie.cookieId);
    await loadCookies();
    showToast("Cookie deleted.");
  } catch (error) {
    console.error("Failed to delete cookie:", error);
    showToast("Failed to delete cookie.", "error");
  }
};

const toggleArchive = async (cookie) => {
  if (!cookie.archived) {
    archiveTarget.value = cookie;
    return;
  }
  await setCookieArchived(cookie, false);
};

const confirmArchive = async () => {
  const cookie = archiveTarget.value;
  archiveTarget.value = null;
  if (!cookie) return;
  await setCookieArchived(cookie, true);
};

const setCookieArchived = async (cookie, archived) => {
  try {
    if (archived) {
      await cookieApi.archive(cookie.cookieId);
      showToast("Cookie archived.");
    } else {
      await cookieApi.unarchive(cookie.cookieId);
      showToast("Cookie unarchived.");
    }
    await loadCookies();
  } catch (error) {
    console.error("Failed to update archive status:", error);
    showToast("Failed to update cookie.", "error");
  }
};

onMounted(loadCookies);
</script>

<template>
  <div class="container-magic pt-28 pb-10">
    <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-8">
      <h1 class="text-h2">Admin Dashboard</h1>
      <BaseButton variant="primary" @click="openForm()">Add Cookie</BaseButton>
    </div>

    <div class="flex flex-col md:flex-row gap-4 mb-8">
      <SearchBar v-model="search" placeholder="Search cookies..." class="flex-1" />
      <BaseInput
        v-model="category"
        type="select"
        :options="categoryOptions"
        class="md:w-64 shrink-0"
      />
    </div>

    <div v-if="loading" class="flex flex-col items-center justify-center py-24">
      <LoadingSpinner label="Loading cookies..." />
    </div>

    <div
      v-else-if="filteredCookies.length === 0"
      class="flex flex-col items-center justify-center py-24 text-center"
    >
      <div class="text-5xl mb-4">🍪</div>
      <h3 class="text-xl font-semibold text-chocolate mb-2">No cookies found</h3>
      <p class="text-chocolate/60">Try adjusting your search or filters.</p>
    </div>

    <CookieGrid
      v-else
      :cookies="filteredCookies"
      @edit="openForm"
      @delete="confirmDelete"
      @archive="toggleArchive"
    />

    <CookieForm :open="showForm" :cookie="selectedCookie" @save="saveCookie" @close="closeForm" />

    <DeleteModal
      :open="!!deleteTarget"
      :cookie="deleteTarget"
      @confirm="deleteCookie"
      @close="deleteTarget = null"
    />

    <ConfirmModal
      :open="!!archiveTarget"
      title="Archive cookie?"
      :message="`${archiveTarget?.description} will be hidden from customers until you restore it.`"
      confirm-text="Archive"
      variant="danger"
      @confirm="confirmArchive"
      @close="archiveTarget = null"
    />
  </div>
</template>
