<template>
  <div class="bg-[#5b2b22] rounded-3xl p-6 space-y-5">
    <div>
      <p class="text-xs uppercase tracking-widest text-cream-100/50 mb-1">
        Cookie ID
      </p>
      <p class="text-cream-50 font-medium">
        {{ cookie.cookieId }}
      </p>
    </div>

    <div>
      <p class="text-xs uppercase tracking-widest text-cream-100/50 mb-1">
        Category
      </p>
      <span
        class="inline-flex rounded-full bg-primary/20 text-primary px-3 py-1 text-sm font-semibold"
      >
        {{ cookie.category }}
      </span>
    </div>

    <div>
      <p class="text-xs uppercase tracking-widest text-cream-100/50 mb-2">
        Ingredients
      </p>
      <p class="text-cream-100/80 leading-7">
        {{ cookie.ingredients }}
      </p>
    </div>

    <div>
      <p class="text-xs uppercase tracking-widest text-cream-100/50 mb-2">
        Allergy Information
      </p>
      <div
        class="inline-flex rounded-full border border-red-400/40 bg-red-500/10 px-3 py-1 text-red-200 text-sm"
      >
        {{ cookie.allergies }}
      </div>
    </div>

    <div>
      <p class="text-xs uppercase tracking-widest text-cream-100/50 mb-2">
        Description
      </p>
      <p class="text-cream-100/80 leading-7">
        {{ cookie.description }}
      </p>
    </div>
  </div>
</template>

<script setup>
defineProps({
  cookie: {
    type: Object,
    required: true,
  },
});
</script>
