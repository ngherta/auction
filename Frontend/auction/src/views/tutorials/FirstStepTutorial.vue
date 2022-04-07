<template>
  <VOnboardingWrapper ref="wrapper" :steps="steps"/>
  <home @handleAuctionIdForTutorial="receiveAuctionId($event)"/>
</template>

<script>
import {defineComponent, ref, onMounted} from 'vue'
import {VOnboardingWrapper, useVOnboarding} from 'v-onboarding'
import 'v-onboarding/dist/style.css'
import Home from '../Home';
import {useRouter} from "vue-router";

export default defineComponent({
  components: {
    VOnboardingWrapper,
    Home
  },
  data() {
    return {
      auctionLinkId: 'x'
    }
  },
  setup() {
    const wrapper = ref(null);
    const router = useRouter();
    const steps = [
      {
        attachTo: {
          element: "#auctions-link-item",// or () => document.querySelector("#foo")
          classList: ["attached", "bar"] // optional. Default: []
        },
        content: { // optional
          title: "Auctions",
          description: "Here you can see more auctions." // optional
        },
        on: {
          beforeStep: function () {
          }, // optional (could be async)
          after: function () {
          } // optional (could be async)
        },
        options: {} // [Options](#options)
      },
      {
        attachTo: {
          element: "#notification-item",// or () => document.querySelector("#foo")
          classList: ["attached", "bar"] // optional. Default: []
        },
        content: { // optional
          title: "Notifications",
          description: "You will receive live notifications about different auctions, statuses of payments and another things." // optional
        },
        on: { // optional
          before: function () {
          }, // optional (could be async)
          after: function () {
          } // optional (could be async)
        },
        options: {} // [Options](#options)
      },
      {
        attachTo: {
          element: "#auction-item-0",// or () => document.querySelector("#foo")
          classList: ["attached", "bar"] // optional. Default: []
        },
        content: { // optional
          title: "Auction",
          description: "This is an auction. You can see more information by click on it." // optional
        },
        on: { // optional
          beforeStep: function () {
          }, // optional (could be async)
          afterStep: function () {
            router.push({
              name: "tutorialSecondPage",
              params: {
                id : 9999
              }
            });
          }
        },
        options: {} // [Options](#options)
      },
      {
        attachTo: {
          element: "#auction-item-0",// or () => document.querySelector("#foo")
          classList: ["attached", "bar"] // optional. Default: []
        },
        content: { // optional
          title: "Auctions-page",
          description: "Auctions page." // optional
        },
        on: { // optional
          beforeStep: function () {
          }, // optional (could be async)
          afterStep: function () {
          }
        },
        options: {} // [Options](#options)
      }
    ]

    const {start} = useVOnboarding(wrapper)

    onMounted(() => {
      start()
    })

    return {
      wrapper,
      steps
    }
  },
  methods: {
    receiveAuctionId(id) {
      if (id != null) {
        this.auctionLinkId = id;
      }
    }
  },
})
</script>