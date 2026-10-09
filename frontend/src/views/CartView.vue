<template>
  <section class="min-h-screen bg-[#45130d] text-cream-50 pt-28 pb-20">
    <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex items-end justify-between gap-4 border-b border-cream-50/15 pb-6 mb-8">
        <div>
          <p class="text-sm uppercase tracking-[0.18em] text-primary mb-2">Your selection</p>
          <h1 class="text-4xl sm:text-5xl font-bold">Shopping Cart</h1>
        </div>
        <span class="text-cream-50/60">{{ itemCount }} {{ itemCount === 1 ? 'item' : 'items' }}</span>
      </div>

      <div v-if="!items.length" class="py-20 text-center">
        <ShoppingBagIcon class="w-12 h-12 mx-auto text-primary mb-5" />
        <h2 class="text-2xl font-semibold mb-2">Your cart is empty</h2>
        <p class="text-cream-50/60 mb-7">Pick a cookie and it will be waiting here.</p>
        <RouterLink to="/products" class="inline-flex items-center gap-2 bg-primary text-chocolate font-semibold px-6 py-3 rounded-full hover:bg-primary-400 transition-colors">
          Browse cookies <ArrowRightIcon class="w-4 h-4" />
        </RouterLink>
      </div>

      <div v-else class="grid grid-cols-1 lg:grid-cols-[1fr_350px] gap-10 items-start">
        <div class="divide-y divide-cream-50/15">
          <article v-for="item in items" :key="item.cookie.cookieId" class="py-5 first:pt-0 flex gap-4 sm:gap-6">
            <img
              :src="cookieApi.getImageUrl(item.cookie.image) || '/cookie.png'"
              :alt="item.cookie.description"
              class="w-24 h-24 sm:w-32 sm:h-32 rounded-xl object-cover bg-[#5b2b22]"
            />
            <div class="flex-1 min-w-0 flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
              <div class="min-w-0">
                <p class="text-xs uppercase tracking-wider text-primary mb-1">{{ item.cookie.category?.replaceAll('_', ' ') }}</p>
                <h2 class="text-lg sm:text-xl font-semibold truncate">{{ item.cookie.description }}</h2>
                <p class="text-cream-50/60 mt-1">R{{ Number(item.cookie.price || 0).toFixed(2) }} each</p>
              </div>
              <div class="flex items-center justify-between sm:justify-end gap-5">
                <div class="inline-flex items-center border border-cream-50/25 rounded-full overflow-hidden">
                  <button type="button" class="w-9 h-9 hover:bg-cream-50/10" :aria-label="`Decrease ${item.cookie.description} quantity`" @click="setQuantity(item.cookie.cookieId, item.quantity - 1)">-</button>
                  <span class="w-8 text-center tabular-nums">{{ item.quantity }}</span>
                  <button type="button" class="w-9 h-9 hover:bg-cream-50/10" :aria-label="`Increase ${item.cookie.description} quantity`" @click="setQuantity(item.cookie.cookieId, item.quantity + 1)">+</button>
                </div>
                <span class="w-20 text-right font-semibold tabular-nums">R{{ (Number(item.cookie.price || 0) * item.quantity).toFixed(2) }}</span>
                <button type="button" class="p-2 text-cream-50/50 hover:text-primary" :aria-label="`Remove ${item.cookie.description}`" @click="remove(item.cookie.cookieId)">
                  <TrashIcon class="w-5 h-5" />
                </button>
              </div>
            </div>
          </article>
        </div>

        <aside class="bg-[#5b2b22] p-6 sm:p-7 rounded-xl">
          <h2 class="text-xl font-semibold mb-5">Order summary</h2>
          <div class="flex justify-between text-cream-50/70 mb-3">
            <span>Subtotal</span><span>R{{ subtotal.toFixed(2) }}</span>
          </div>
          <div class="flex justify-between border-t border-cream-50/15 pt-4 text-lg font-semibold">
            <span>Total</span><span>R{{ subtotal.toFixed(2) }}</span>
          </div>

          <form class="mt-7" @submit.prevent="linkCustomer">
            <label for="customer-email" class="block text-sm font-medium mb-2">Save cart to your customer account</label>
            <input
              id="customer-email"
              v-model="email"
              type="email"
              required
              autocomplete="email"
              placeholder="you@example.com"
              class="w-full rounded-lg border border-cream-50/20 bg-[#45130d] px-4 py-3 text-cream-50 placeholder:text-cream-50/35 focus:outline-none focus:ring-2 focus:ring-primary"
            />
            <p class="text-xs text-cream-50/50 mt-2">Enter the email on your existing customer account.</p>
            <button type="submit" :disabled="saving" class="w-full mt-4 inline-flex justify-center items-center gap-2 rounded-full bg-primary px-5 py-3 font-semibold text-chocolate hover:bg-primary-400 disabled:opacity-60 transition-colors">
              {{ saving ? 'Saving...' : 'Save cart' }}
            </button>
            <p v-if="customerEmail" class="text-xs text-primary mt-3">Linked to {{ customerEmail }}</p>
            <p v-if="saveError" role="alert" class="text-xs text-red-200 mt-3">Could not link this email. Check that it belongs to an existing customer account.</p>
          </form>
          <RouterLink to="/products" class="mt-5 flex justify-center text-sm text-cream-50/65 hover:text-cream-50 transition-colors">Continue shopping</RouterLink>
        </aside>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { RouterLink } from "vue-router";
import { ArrowRightIcon, ShoppingBagIcon, TrashIcon } from "@heroicons/vue/24/outline";
import cookieApi from "../api/cookieApi";
import { useCart } from "../composables/useCart";

const {
  items,
  customerEmail,
  itemCount,
  subtotal,
  loadCart,
  removeItem,
  setQuantity,
  saveCustomer,
} = useCart();
const email = ref(customerEmail.value);
const saving = ref(false);
const saveError = ref(false);

async function remove(cookieId) {
  await removeItem(cookieId);
}

async function linkCustomer() {
  saving.value = true;
  saveError.value = false;
  const saved = await saveCustomer(email.value);
  saveError.value = !saved;
  saving.value = false;
}

onMounted(loadCart);
</script>
