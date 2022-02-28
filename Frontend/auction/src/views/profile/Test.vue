<template>
  <VOnboardingWrapper ref="wrapper" :steps="steps" />
  <div class="container">
    <button id="foo" class="m-auto">Update</button>
    <button id="foo2" class="m-auto">Update</button>
  </div>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue'
import { VOnboardingWrapper, useVOnboarding } from 'v-onboarding'
import 'v-onboarding/dist/style.css'
export default defineComponent ({
  components: {
    VOnboardingWrapper
  },
  setup() {
    const wrapper = ref(null)
    const steps = [
      {
        attachTo: {
          element: "#foo",// or () => document.querySelector("#foo")
          classList: ["attached", "bar"] // optional. Default: []
        },
        content: { // optional
          title: "This is bytton",
          description: "description of it" // optional
        },
        on: { // optional
          before: function() {}, // optional (could be async)
          after: function() {} // optional (could be async)
        },
        options: {} // [Options](#options)
      },
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