<template>
  <div
      class="bg-[#6a4037] rounded-3xl flex items-center overflow-hidden h-[180px]"
  >
    <div class="w-40 h-40 shrink-0">
      <img
          :src="displayImage"
          :alt="related.description"
          class="w-full h-full object-cover rounded-full p-4"
      />
    </div>

    <div class="p-6 flex-1 flex flex-col justify-center min-w-0">
      <h3 class="text-xl text-cream-50 font-bold mb-1 truncate">
        {{ related.description }}
      </h3>
      <p class="text-cream-100/70 text-sm mb-3 line-clamp-2">
        {{
          related.longDescription || "A CrumbleMagic favorite, freshly baked."
        }}
      </p>
      <div class="flex items-center justify-between">
        <span class="text-cream-50 font-bold text-xl"
        >R{{ related.price?.toFixed(2) }}</span
        >
        <div class="flex items-center gap-2">
          <button
              type="button"
              class="bg-primary text-chocolate text-sm font-semibold px-5 py-2.5 rounded-full hover:bg-primary-400 transition-colors"
              @click="$router.push(`/products/${related.id}`)"
          >
            View More
          </button>
          <button
              type="button"
              class="h-10 w-10 flex items-center justify-center rounded-full bg-cream-50/10 text-cream-50 hover:bg-cream-50/20 transition-colors"
              aria-label="Add to cart"
              @click="$emit('add-to-cart', related)"
          >
            <ShoppingCartIcon class="h-4 w-4" />
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from "vue";
import { ShoppingCartIcon } from "@heroicons/vue/24/outline";
import cookieApi from "../../api/cookieApi";

const props = defineProps({
  related: {
    type: Object,
    required: true,
  },
});

defineEmits(["add-to-cart"]);

const displayImage = computed(
    () => cookieApi.getImageUrl(props.related.image) || "/cookie.png"
);
</script>