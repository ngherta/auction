<template>
  <VOnboardingWrapper ref="wrapper" :steps="steps" />
  <auction-page :chat-message-for-tutorial="chatMessage"/>
</template>

<script>
import { ref, onMounted } from 'vue'
import { VOnboardingWrapper, useVOnboarding } from 'v-onboarding'
import 'v-onboarding/dist/style.css'
import AuctionPage from "@/views/AuctionPage";
const date = new Date();

export default {
  name: "SecondStepTutorial",
  components: {
    VOnboardingWrapper,
    AuctionPage
  },
  data() {
    return {
      chatMessage: {
        message: 'Hello ' + this.$store.state.auth.user.userDto.firstName + ' ' + this.$store.state.auth.user.userDto.lastName + '!',
        chatRoom: 'xxx',
        senderId: '777',
        senderFirstName: 'Nicolae',
        senderLastName: 'Gherta',
        genDate: ("00" + (date.getMonth() + 1)).slice(-2) + "/" +
            ("00" + date.getDate()).slice(-2) + "/" +
            date.getFullYear() + " " +
            ("00" + date.getHours()).slice(-2) + ":" +
            ("00" + date.getMinutes()).slice(-2) + ":" +
            ("00" + date.getSeconds()).slice(-2),
      }
    }
  },
  setup() {
    const wrapper = ref(null)
    const steps = [
      {
        attachTo: {
          element: "#share-button",// or () => document.querySelector("#foo")
          classList: ["attached", "bar"] // optional. Default: []
        },
        content: { // optional
          title: "Share",
          description: "You can share this link with your friends." // optional
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
          element: "#betting-room-container",// or () => document.querySelector("#foo")
          classList: ["attached", "bar"] // optional. Default: []
        },
        content: { // optional
          title: "Betting",
          description: "Here you can see and put new bid." // optional
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
          element: "#chat-container",// or () => document.querySelector("#foo")
          classList: ["attached", "bar"] // optional. Default: []
        },
        content: { // optional
          title: "Chat",
          description: "There is a live chat. You can chat with other users." // optional
        },
        on: { // optional
          before: function () {
          }, // optional (could be async)
          after: function () {
          } // optional (could be async)
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
  }
}
</script>

<style scoped>

</style>