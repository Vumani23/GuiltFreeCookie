<script setup>
import { computed } from "vue";
import { useRouter } from "vue-router";
import { ShoppingBagIcon, EyeIcon } from "@heroicons/vue/24/outline";
import BaseBadge from "../../components/ui/BaseBadge.vue";
import { useFormat } from "../../composables/useFormat";

const props = defineProps({
  cookie: {
    type: Object,
    required: true,
  },
});

const router = useRouter();
const { truncate } = useFormat();

const displayBadge = computed(() =>
    props.cookie.category.replace(/_/g, " ")
);

const goToDetails = () => {
  router.push({
    name: "ProductDetail",
    params: {
      id: props.cookie.cookieId,
    },
  });
};
</script>

<template>
  <article
      class="group relative overflow-hidden rounded-4xl p-6 cursor-pointer transition-all duration-500 ease-cookie bg-surface text-chocolate shadow-soft hover:shadow-soft-lg hover:-translate-y-1.5"
      @click="goToDetails"
  >
    <div
        class="absolute top-5 left-5 z-10 flex flex-col gap-1.5 items-start"
    >
      <BaseBadge
          variant="chocolate"
          size="sm"
      >
        {{ displayBadge }}
      </BaseBadge>
    </div>

    <button
        type="button"
        class="absolute top-5 right-5 z-10 flex items-center justify-center h-10 w-10 rounded-full transition-all duration-300 bg-cream-100 text-chocolate/60 hover:bg-primary hover:text-chocolate"
        aria-label="Quick add to cart"
        @click.stop
    >
      <ShoppingBagIcon class="h-5 w-5" />
    </button>

    <div class="relative flex items-center justify-center mb-6 mt-8">
      <div
          class="absolute inset-0 -m-4 rounded-full bg-primary/30 blur-2xl opacity-0 group-hover:opacity-100 transition-opacity duration-500"
      />

      <div
          class="relative w-44 h-44 rounded-full overflow-hidden transition-transform duration-700 ease-cookie group-hover:scale-110 group-hover:rotate-6 shadow-cookie ring-4 ring-cream-100"
      >
        <img
            :src="props.cookie.image"
            :alt="props.cookie.description"
            loading="lazy"
            class="w-full h-full object-cover"
        />
      </div>
    </div>

    <div class="space-y-2.5">
      <h3 class="text-xl font-semibold text-chocolate">
        {{ truncate(props.cookie.description, 40) }}
      </h3>

      <p class="text-card leading-relaxed min-h-[3.5rem] text-chocolate/60">
        {{ truncate(props.cookie.ingredients, 90) }}
      </p>

      <div class="flex items-center justify-between pt-3">
        <span class="text-2xl font-bold text-chocolate">
          R{{ props.cookie.price }}
        </span>

        <button
            type="button"
            class="inline-flex items-center gap-1.5 px-4 py-2.5 rounded-full text-sm font-medium transition-all duration-300 bg-cream-100 text-chocolate hover:bg-cream-200"
            @click.stop="goToDetails"
        >
          <EyeIcon class="h-4 w-4" />
          View More
        </button>
      </div>
    </div>
  </article>
</template>