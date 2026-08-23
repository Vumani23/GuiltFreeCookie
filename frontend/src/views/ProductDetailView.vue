<template>
  <div class="min-h-screen bg-[#45130d] relative">
    <div
      class="absolute inset-0 pointer-events-none z-0"
      style="
        background-image: url(&quot;/images/cookie-pattern.png&quot;);
        background-size: cover;
        background-position: center;
        opacity: 0.08;
      "
    />

    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-14 relative z-10">
      <router-link
        to="/products"
        class="inline-flex items-center gap-1.5 text-sm font-medium text-cream-100/60 hover:text-cream-100 transition-colors mb-8"
      >
        <ArrowLeftIcon class="h-4 w-4" />
        Back to Cookies
      </router-link>

      <div
        v-if="loading"
        class="flex flex-col items-center justify-center py-32"
      >
        <LoadingSpinner />
        <p class="text-cream-100/60 mt-4">Loading cookie details...</p>
      </div>

      <div
        v-else-if="!cookie"
        class="flex flex-col items-center justify-center py-32 text-center"
      >
        <div class="text-5xl mb-4">😕</div>
        <h3 class="text-xl font-semibold text-cream-100 mb-2">
          Cookie Not Found
        </h3>
        <p class="text-cream-100/60 mb-6">
          The cookie you're looking for doesn't exist.
        </p>
        <BaseButton @click="$router.push('/products')"
          >Browse All Cookies</BaseButton
        >
      </div>

      <template v-else>
        <div
          class="grid grid-cols-1 lg:grid-cols-[1.1fr_0.9fr] gap-20 items-start"
          v-reveal
        >
          <div class="lg:sticky lg:top-24">
            <div
              class="bg-[#5b2b22] rounded-[30px] p-10 flex items-center justify-center shadow-xl"
            >
              <img
                :src="displayImage"
                :alt="cookie.description"
                class="w-full max-w-[420px] object-contain"
              />
            </div>
          </div>

          <div class="space-y-6">
            <div>
              <p class="text-sm font-medium text-cream-100/50 mb-2">
                GuiltFreeCookie
              </p>
              <h1 class="text-5xl font-bold leading-tight text-cream-50 mb-3">
                {{ cookie.description }}
              </h1>

              <div class="flex items-center gap-1.5 mb-4">
                <StarIcon
                  v-for="n in 5"
                  :key="n"
                  class="h-5 w-5"
                  :class="
                    n <= displayRating ? 'text-primary' : 'text-cream-100/20'
                  "
                />
                <span class="text-cream-100/60 text-sm ml-1"
                  >({{ displayRating.toFixed(1) }})</span
                >
              </div>

              <div class="text-5xl font-bold text-cream-50">
                R{{ cookie.price?.toFixed(2) }}
              </div>
            </div>

            <CookieInfoCard :cookie="cookie" />

            <QuantitySelector v-model="quantity" />

            <p
              v-if="cookie.longDescription"
              class="text-cream-100/80 leading-8 max-w-xl"
            >
              {{ cookie.longDescription }}
            </p>

            <div class="flex flex-col sm:flex-row gap-4 pt-4">
              <button
                type="button"
                class="w-56 inline-flex items-center justify-center gap-2 bg-primary text-chocolate text-lg font-semibold py-4 rounded-full hover:bg-primary-400 transition-colors duration-300"
                @click="addToCart"
              >
                <ShoppingBagIcon class="h-5 w-5" />
                Add to Cart
              </button>
              <button
                type="button"
                class="w-56 inline-flex items-center justify-center gap-2 bg-cream-50 text-chocolate text-lg font-semibold py-4 rounded-full hover:bg-cream-200 transition-colors duration-300"
                @click="buyNow"
              >
                Buy Now
              </button>
            </div>
          </div>
        </div>

        <div v-if="relatedCookies.length" class="mt-20">
          <h2 class="text-3xl font-bold text-cream-50 mb-8">
            You may also like
          </h2>
          <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
            <RelatedCookieCard
              v-for="related in relatedCookies"
              :key="related.id"
              :related="related"
            />
          </div>
        </div>
      </template>
    </div>

    <NewsletterSection />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useRoute } from "vue-router";
import { ArrowLeftIcon, ShoppingBagIcon } from "@heroicons/vue/24/outline";
import { StarIcon } from "@heroicons/vue/24/solid";

import cookieApi from "../api/cookieApi";
import BaseButton from "../components/ui/BaseButton.vue";
import LoadingSpinner from "../components/ui/LoadingSpinner.vue";
import CookieInfoCard from "../components/cookieDetails/CookieInfoCard.vue";
import QuantitySelector from "../components/cookieDetails/QuantitySelector.vue";
import RelatedCookieCard from "../components/cookieDetails/RelatedCookieCard.vue";
import NewsletterSection from "../components/sections/NewsletterSection.vue";
import { useScrollReveal } from "../composables/useScrollReveal";

const { vReveal } = useScrollReveal();
const route = useRoute();

const cookie = ref(null);
const loading = ref(false);
const quantity = ref(1);
const relatedCookies = ref([]);

const displayImage = computed(
  () => cookieApi.getImageUrl(cookie.value?.image) || "/cookie.png",
);

const displayRating = computed(() => cookie.value?.rating ?? 5);

const addToCart = () => {};

const buyNow = () => {
  addToCart();
};

const fetchCookie = async () => {
  loading.value = true;
  try {
    cookie.value = await cookieApi.getById(route.params.id);
  } catch (error) {
    cookie.value = null;
  } finally {
    loading.value = false;
  }
};

const fetchRelated = async () => {
  try {
    const all = await cookieApi.getAll?.();
    if (Array.isArray(all)) {
      relatedCookies.value = all
        .filter((c) => c.id !== route.params.id)
        .slice(0, 2);
    }
  } catch (error) {
    relatedCookies.value = [];
  }
};

onMounted(async () => {
  await fetchCookie();
  fetchRelated();
});
</script>
