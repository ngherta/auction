<template>
  <span id="timer"></span>
</template>

<script>

export default {
  name: "Test",
  props: ['date'],
  methods: {
    count() {
      // console.log("date")
      // console.log(this.date)
      // Set the date we're counting down to
      let countDownDate = this.date.getTime();
      // console.log(this.date)
      // Update the count down every 1 second
      let x = setInterval(function () {

        // Get today's date and time
        let now = new Date().getTime();

        // Find the distance between now and the count down date
        let distance = countDownDate - now;

        // Time calculations for days, hours, minutes and seconds
        let days = Math.floor(distance / (1000 * 60 * 60 * 24));
        let hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        let minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        let seconds = Math.floor((distance % (1000 * 60)) / 1000);

        // Display the result in the element with id="timer"
        let timer = (days === 0 ? "" : days + "d ") +
            (hours === 0 ? "" : hours + "h ") +
            (minutes === 0 ? "" : minutes + "m ") +
            (seconds === 0 ? "" : seconds + "s");

        document.getElementById("timer").innerHTML = timer;

        // If the count down is finished, write some text
        if (distance < 0) {
          clearInterval(x);
          document.getElementById("timer").innerHTML = "EXPIRED";
        }
      }, 1000);
    }
  },
  mounted() {
    console.log(this.date)
    this.count();
  }

}
</script>