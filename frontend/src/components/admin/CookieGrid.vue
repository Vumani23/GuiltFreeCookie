<script setup>
import {
  PencilSquareIcon,
  TrashIcon,
  ArchiveBoxIcon,
  ArchiveBoxXMarkIcon,
} from "@heroicons/vue/24/outline";
import cookieApi from "../../api/cookieApi";
import { formatCategoryLabel } from "../../constants/cookieCategories";
import BaseBadge from "../ui/BaseBadge.vue";

defineProps({
  cookies: {
    type: Array,
    required: true,
  },
});

const emit = defineEmits(["edit", "delete", "archive"]);

const displayImage = (cookie) =>
  cookieApi.getImageUrl(cookie.image) || "/cookie.png";
</script>

<template>
  <div
    class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6"
  >
    <article
      v-for="cookie in cookies"
      :key="cookie.cookieId"
      class="card p-5 flex flex-col gap-4"
      :class="{ 'opacity-60': cookie.archived }"
    >
      <div
        class="relative w-full aspect-square rounded-2xl overflow-hidden bg-cream-100"
      >
        <img
          :src="displayImage(cookie)"
          :alt="cookie.description"
          class="w-full h-full object-cover"
        />
        <BaseBadge
          v-if="cookie.archived"
          variant="cream"
          size="sm"
          class="absolute top-3 left-3"
        >
          Archived
        </BaseBadge>
      </div>

      <div class="flex-1 space-y-2">
        <BaseBadge variant="primary" size="sm">{{
          formatCategoryLabel(cookie.category)
        }}</BaseBadge>
        <h3 class="text-lg font-semibold text-chocolate leading-snug">
          {{ cookie.description }}
        </h3>
        <p class="text-2xl font-bold text-chocolate">R{{ cookie.price }}</p>
      </div>

      <div class="flex gap-2">
        <button
          type="button"
          class="flex-1 inline-flex items-center justify-center gap-1.5 rounded-full bg-primary/15 text-primary-700 text-sm font-medium py-2.5 hover:bg-primary/25 transition-colors duration-300"
          @click="emit('archive', cookie)"
        >
          <ArchiveBoxXMarkIcon v-if="cookie.archived" class="h-4 w-4" />
          <ArchiveBoxIcon v-else class="h-4 w-4" />
          {{ cookie.archived ? "Restore" : "Archive" }}
        </button>
        <button
          type="button"
          class="h-10 w-10 shrink-0 inline-flex items-center justify-center rounded-full bg-cream-100 text-chocolate hover:bg-cream-200 transition-colors duration-300"
          aria-label="Edit"
          title="Edit"
          @click="emit('edit', cookie)"
        >
          <PencilSquareIcon class="h-4 w-4" />
        </button>

        <button
          type="button"
          class="h-10 w-10 shrink-0 inline-flex items-center justify-center rounded-full bg-red-50 text-red-600 hover:bg-red-100 transition-colors duration-300"
          aria-label="Delete"
          title="Delete"
          @click="emit('delete', cookie)"
        >
          <TrashIcon class="h-4 w-4" />
        </button>
      </div>
    </article>
  </div>
</template>
