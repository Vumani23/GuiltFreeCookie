<script setup>
import { ref, onMounted } from "vue";
import cookieApi from "../api/cookieApi";
import CookieForm from "../components/admin/CookieForm.vue";

const cookies = ref([]);
const showForm = ref(false);
const selectedCookie = ref(null);

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
  try {
    cookies.value = await cookieApi.getAll();
  } catch (error) {
    console.error("Failed to load cookies:", error);
  }
};

const saveCookie = async (cookie) => {
  try {
    await cookieApi.save(cookie);
    closeForm();
    await loadCookies();
  } catch (error) {
    console.error("Failed to save cookie:", error);
  }
};

const deleteCookie = async (id) => {
  if (!confirm("Delete this cookie?")) return;

  try {
    await cookieApi.delete(id);
    await loadCookies();
  } catch (error) {
    console.error("Failed to delete cookie:", error);
  }
};

onMounted(loadCookies);
</script>

<template>
  <div class="p-8">
    <h1 class="text-3xl font-bold mb-6">Admin Dashboard</h1>

    <button
      class="bg-primary px-5 py-2 rounded text-white mb-6"
      @click="openForm()"
    >
      Add Cookie
    </button>

    <table class="w-full border-collapse">
      <thead>
        <tr class="border-b">
          <th class="text-left p-2">Name</th>
          <th class="text-left p-2">Category</th>
          <th class="text-left p-2">Price</th>
          <th class="text-left p-2">Actions</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="cookie in cookies" :key="cookie.cookieId" class="border-b">
          <td class="p-2">{{ cookie.description }}</td>
          <td class="p-2">{{ cookie.category }}</td>
          <td class="p-2">R {{ cookie.price }}</td>

          <td class="p-2">
            <button class="text-blue-600 mr-4" @click="openForm(cookie)">
              Edit
            </button>

            <button class="text-red-600" @click="deleteCookie(cookie.cookieId)">
              Delete
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <CookieForm
      v-if="showForm"
      :cookie="selectedCookie"
      @save="saveCookie"
      @close="closeForm"
    />
  </div>
</template>
