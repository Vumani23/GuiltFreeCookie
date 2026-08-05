<script setup>
/**
 * FaqSection — homepage FAQ accordion section.
 * Tailored for diabetic, keto, and health-conscious customers.
 */
import AccordionList from "../../components/ui/AccordionList.vue";
import { faqs } from "../../data/cookies";
import { useScrollReveal } from "../../composables/useScrollReveal";

const { vReveal } = useScrollReveal();
</script>

<template>
  <section class="section bg-background relative overflow-hidden">
    <div class="container-magic max-w-4xl px-4 sm:px-6 lg:px-8">
      <div v-reveal class="text-center mb-10 lg:mb-14">
        <span class="eyebrow block mb-2">Guilt-Free Answers</span>
        <h2 class="text-3xl md:text-4xl text-chocolate leading-tight font-bold">
          Frequently Asked <span class="text-primary">Questions</span>
        </h2>
        <p class="text-chocolate/70 text-card max-w-2xl mx-auto mt-4">
          Everything you need to know about our diabetic-friendly ingredients,
          low-GI sweeteners, macros, and nationwide South African delivery.
        </p>
      </div>

      <div v-reveal data-reveal-delay="100">
        <AccordionList :items="faqs" />
      </div>
    </div>
  </section>
</template>