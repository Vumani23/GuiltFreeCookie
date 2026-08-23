<script setup>
import {
  PencilSquareIcon,
  TrashIcon,
  KeyIcon,
  NoSymbolIcon,
  CheckCircleIcon,
} from "@heroicons/vue/24/outline";
import BaseBadge from "../ui/BaseBadge.vue";

defineProps({
  admins: {
    type: Array,
    required: true,
  },
  currentAdminId: {
    type: String,
    default: null,
  },
});

const emit = defineEmits(["edit", "delete", "toggleActive", "resetPassword"]);
</script>

<template>
  <div class="space-y-4">
    <article
      v-for="admin in admins"
      :key="admin.adminId"
      class="card p-5 flex flex-col sm:flex-row sm:items-center gap-4"
      :class="{ 'opacity-60': !admin.active }"
    >
      <div class="flex-1 min-w-0">
        <div class="flex items-center gap-2 mb-1 flex-wrap">
          <h3 class="text-lg font-semibold text-chocolate truncate">
            {{ admin.firstName }} {{ admin.lastName }}
          </h3>
          <BaseBadge :variant="admin.active ? 'success' : 'warning'" size="sm">
            {{ admin.active ? "Active" : "Inactive" }}
          </BaseBadge>
          <BaseBadge v-if="admin.adminId === currentAdminId" variant="cream" size="sm">
            You
          </BaseBadge>
        </div>
        <p class="text-chocolate/60 text-sm truncate">{{ admin.email }}</p>
      </div>

      <div class="flex gap-2 shrink-0 flex-wrap">
        <button
          type="button"
          class="h-10 w-10 shrink-0 inline-flex items-center justify-center rounded-full bg-cream-100 text-chocolate hover:bg-cream-200 transition-colors duration-300"
          aria-label="Edit"
          title="Edit"
          @click="emit('edit', admin)"
        >
          <PencilSquareIcon class="h-4 w-4" />
        </button>

        <button
          type="button"
          class="inline-flex items-center justify-center gap-1.5 rounded-full bg-primary/15 text-primary-700 text-sm font-medium px-4 py-2.5 hover:bg-primary/25 transition-colors duration-300"
          @click="emit('resetPassword', admin)"
        >
          <KeyIcon class="h-4 w-4" />
          Reset Password
        </button>

        <button
          type="button"
          class="inline-flex items-center justify-center gap-1.5 rounded-full bg-cream-100 text-chocolate text-sm font-medium px-4 py-2.5 hover:bg-cream-200 transition-colors duration-300 disabled:opacity-40 disabled:cursor-not-allowed"
          :disabled="admin.adminId === currentAdminId"
          :title="admin.adminId === currentAdminId ? 'Cannot deactivate your own account' : ''"
          @click="emit('toggleActive', admin)"
        >
          <CheckCircleIcon v-if="!admin.active" class="h-4 w-4" />
          <NoSymbolIcon v-else class="h-4 w-4" />
          {{ admin.active ? "Deactivate" : "Activate" }}
        </button>

        <button
          type="button"
          class="h-10 w-10 shrink-0 inline-flex items-center justify-center rounded-full bg-red-50 text-red-600 hover:bg-red-100 transition-colors duration-300 disabled:opacity-40 disabled:cursor-not-allowed"
          aria-label="Delete"
          :title="admin.adminId === currentAdminId ? 'Cannot delete your own account' : 'Delete'"
          :disabled="admin.adminId === currentAdminId"
          @click="emit('delete', admin)"
        >
          <TrashIcon class="h-4 w-4" />
        </button>
      </div>
    </article>
  </div>
</template>
