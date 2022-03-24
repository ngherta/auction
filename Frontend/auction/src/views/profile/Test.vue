<template>
  <VOnboardingWrapper ref="wrapper" :steps="steps" />
  <home/>
  <div class="container">
    <button id="foo" class="m-auto">Update</button>
    <button id="foo2" class="m-auto">Update</button>
  </div>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue'
import { VOnboardingWrapper, useVOnboarding } from 'v-onboarding'
import 'v-onboarding/dist/style.css'
import Home from '../Home';

export default defineComponent ({
  components: {
    VOnboardingWrapper,
    Home
  },
  setup() {
    const wrapper = ref(null)
    const steps = [
      {
        attachTo: {
          element: "#foo2",// or () => document.querySelector("#foo")
          classList: ["attached", "bar"] // optional. Default: []
        },
        content: { // optional
          title: "This is bytton",
          description: "description of it" // optional
        },
        on: { // optional
          before: function() {
            console.log("asdasdasdasd")
            this.$router.push("/home")
          }, // optional (could be async)
          after: function() {
            console.log("asdasdasdasd")

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
          before: function() {}, // optional (could be async)
          after: function() {

          } // optional (could be async)
        },
        options: {} // [Options](#options)
      }
    ]

    const { start } = useVOnboarding(wrapper)

    onMounted(() => {
      start()
    })

    return {
      wrapper,
      steps
    }
  }
})
</script>