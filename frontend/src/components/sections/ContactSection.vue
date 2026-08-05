<script setup>
import { ref } from "vue";
import { PhoneIcon, EnvelopeIcon, MapPinIcon } from "@heroicons/vue/24/outline";
import BaseInput from "../../components/ui/BaseInput.vue";
import BaseButton from "../../components/ui/BaseButton.vue";
import { useScrollReveal } from "../../composables/useScrollReveal";

const { vReveal } = useScrollReveal();

const form = ref({
  name: "",
  email: "",
  subject: "",
  message: "",
});

const submitting = ref(false);

const contactCards = [
  {
    icon: PhoneIcon,
    label: "Phone",
    value: "+27 82 851 3174",
    sub: "Call or WhatsApp us",
  },
  {
    icon: EnvelopeIcon,
    label: "Email",
    value: "hello@guiltfreecookie.co.za",
    sub: "We reply within 24 hours",
  },
  {
    icon: MapPinIcon,
    label: "Address",
    value: "41 Caravel Road, Strandfontein",
    sub: "Cape Town City, 7798",
  },
];

const onSubmit = async () => {
  if (!form.value.name || !form.value.email || !form.value.message) {
    alert("Please complete all required fields.");
    return;
  }

  submitting.value = true;

  await new Promise((resolve) => setTimeout(resolve, 800));

  submitting.value = false;

  alert("Message sent! We will be in touch shortly.");

  form.value = {
    name: "",
    email: "",
    subject: "",
    message: "",
  };
};
</script>

<template>
  <section class="section bg-background relative overflow-hidden">
    <div class="container-magic px-4 sm:px-6 lg:px-8">
      <div v-reveal class="text-center mb-10 lg:mb-14 max-w-2xl mx-auto">
        <span class="eyebrow block mb-2">Get In Touch</span>
        <h2 class="text-3xl md:text-4xl text-chocolate leading-tight font-bold">
          Let's Talk <span class="text-primary">Cookies</span>
        </h2>
        <p class="text-chocolate/70 text-card mt-4">
          For inquiries, custom orders, or support, feel free to reach out. Our
          team will connect with you promptly!
        </p>
      </div>

      <div
          class="grid grid-cols-1 lg:grid-cols-2 rounded-3xl lg:rounded-5xl overflow-hidden shadow-soft-lg bg-surface"
      >
        <!-- Left: contact info (dark chocolate) -->
        <div
            class="bg-chocolate text-cream p-6 sm:p-8 lg:p-12 relative overflow-hidden texture-dark"
        >
          <h3 class="text-xl sm:text-2xl font-semibold text-cream mb-2">
            Contact Information
          </h3>
          <p class="text-cream/70 text-sm sm:text-base text-card mb-8 lg:mb-10">
            Reach out through any of the channels below — we usually respond
            within a few hours.
          </p>

          <div class="space-y-4">
            <div
                v-for="card in contactCards"
                :key="card.label"
                class="flex items-start sm:items-center gap-4 bg-chocolate-400/40 border border-cream/10 rounded-2xl sm:rounded-3xl p-4 hover:bg-chocolate-300/50 transition-all duration-300"
            >
              <span
                  class="flex items-center justify-center h-10 w-10 sm:h-12 sm:w-12 rounded-full bg-primary/20 text-primary shrink-0 mt-1 sm:mt-0"
              >
                <component :is="card.icon" class="h-5 w-5" />
              </span>
              <div>
                <div class="text-cream/60 text-xs sm:text-sm uppercase tracking-wider">
                  {{ card.label }}
                </div>
                <div class="text-cream font-semibold text-base sm:text-lg">
                  {{ card.value }}
                </div>
                <div class="text-cream/50 text-xs sm:text-sm mt-0.5">{{ card.sub }}</div>
              </div>
            </div>
          </div>

          <!-- Decorative cookie watermark -->
          <div
              class="pointer-events-none absolute -bottom-16 -left-16 w-48 h-48 lg:w-64 lg:h-64 rounded-full bg-primary/5 blur-2xl"
          />
        </div>

        <!-- Right: form (cream) -->
        <div class="bg-cream-50/50 p-6 sm:p-8 lg:p-12">
          <h3 class="text-xl sm:text-2xl font-semibold text-chocolate mb-6">
            Send us a message
          </h3>
          <form class="space-y-5" @submit.prevent="onSubmit">
            <div class="grid grid-cols-1 sm:grid-cols-2 gap-4 sm:gap-5">
              <BaseInput
                  v-model="form.name"
                  label="Your Name"
                  placeholder="Sarah Ndlovu"
                  required
              />
              <BaseInput
                  v-model="form.email"
                  label="Your Email"
                  type="email"
                  placeholder="sarah@example.co.za"
                  required
              />
            </div>
            <BaseInput
                v-model="form.subject"
                label="Subject"
                placeholder="Custom cookie order for an event"
            />
            <BaseInput
                v-model="form.message"
                label="Message"
                type="textarea"
                :rows="5"
                placeholder="Tell us how we can help..."
                required
            />
            <BaseButton
                type="submit"
                variant="primary"
                size="lg"
                block
                :loading="submitting"
                class="w-full mt-2"
            >
              {{ submitting ? "Sending..." : "Send Message" }}
            </BaseButton>
          </form>
        </div>
      </div>
    </div>
  </section>
</template>