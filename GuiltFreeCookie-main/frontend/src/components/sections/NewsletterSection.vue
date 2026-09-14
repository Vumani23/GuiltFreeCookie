<template>
  <section class="bg-[#F6F0E6] py-24 relative overflow-hidden">
    <img
        src="/cookie-background.jpeg"
        class="absolute left-0 bottom-0 opacity-10 w-72 pointer-events-none"
        alt=""
    />

    <div
        class="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8 text-center relative z-10"
    >
      <h2 class="text-6xl font-bold text-chocolate mb-6">
        Join in and get 10% off!
      </h2>
      <p
          class="text-chocolate/70 text-lg mb-10 leading-relaxed max-w-2xl mx-auto"
      >
        Subscribe to our exclusive newsletter today and instantly receive a
        special 10% discount code for your next order! Be the first to know
        about restocks and special offers.
      </p>
      <form
          class="flex flex-col sm:flex-row gap-4 justify-center max-w-lg mx-auto"
          @submit.prevent="subscribe"
      >
        <div class="relative flex-1">
          <EnvelopeIcon
              class="h-6 w-6 text-chocolate/40 absolute left-5 top-1/2 -translate-y-1/2"
          />
          <input
              v-model="email"
              type="email"
              required
              placeholder="Email address"
              class="w-full pl-14 pr-5 py-4 rounded-full bg-white border border-chocolate/10 text-chocolate placeholder:text-chocolate/40 focus:outline-none focus:ring-2 focus:ring-primary text-lg"
          />
        </div>
        <button
            type="submit"
            class="bg-chocolate text-cream-50 font-semibold px-8 py-4 rounded-full hover:bg-chocolate/90 transition-colors whitespace-nowrap text-lg"
        >
          {{ subscribed ? "Subscribed!" : "Subscribe" }}
        </button>
      </form>
    </div>
  </section>
</template>

<script setup>
import { ref } from "vue";
import { EnvelopeIcon } from "@heroicons/vue/24/outline";

const email = ref("");
const subscribed = ref(false);

const subscribe = () => {
  if (!email.value) return;
  subscribed.value = true;
  email.value = "";
};
</script>