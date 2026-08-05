<script setup>
const cookies = ["/cookie2.png", "/cookie.png", "/cookie3.png", "/cookie4.png"];
</script>

<template>
  <section class="bg-[#e3d9c8] h-[50vh] min-h-[420px] flex items-center">
    <div class="mx-auto w-full max-w-7xl px-6 sm:px-8 lg:px-16">
      <div class="grid lg:grid-cols-2 items-center gap-12">
        <div
          class="order-2 lg:order-1 flex justify-center items-center max-w-lg mx-auto mt-0 lg:mt-0"
        >
          <img
            v-for="(cookie, index) in cookies"
            :key="index"
            :src="cookie"
            :alt="`Cookie ${index + 1}`"
            :class="[
              'w-36 lg:w-60 object-contain transition duration-500 hover:scale-110 mb-20 lg:mb-0',
              index !== 0 ? '-ml-20' : '',
            ]"
          />
        </div>

        <div class="mt-20 order-1 lg:order-2 lg:mt-0 lg:ml-20 text-center">
          <h2
            class="text-secondary font-semibold leading-tight text-3xl sm:text-4xl lg:text-4xl"
          >
            Wholesome Ingredients
            <span class="text-primary">Crafted for Better Living</span>
          </h2>

          <p
            class="hidden lg:block lg:mb-[-25px] lg:mt-6 text-chocolate lg:text-base text-lg lg:leading-7"
          >
            Every cookie is thoughtfully baked using premium, health-conscious
            ingredients with less sugar, more nutrition, and great taste.
            Whether you're managing diabetes, maintaining a healthy lifestyle,
            or looking for a high-protein snack, our recipes are made to satisfy
            your cravings without compromising your wellness goals.
          </p>
        </div>
      </div>
    </div>
  </section>
</template>
