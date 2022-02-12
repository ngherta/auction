<template>
  <h1 class="text-center m-5">
    SUCCESS PAY
  </h1>

  <div class="swal2-icon swal2-success swal2-animate-success-icon" style="display: flex;">
    <div class="swal2-success-circular-line-left" style="background-color: rgb(255, 255, 255);"></div>
    <span class="swal2-success-line-tip"></span>
    <span class="swal2-success-line-long"></span>
    <div class="swal2-success-ring"></div>
    <div class="swal2-success-fix" style="background-color: rgb(255, 255, 255);"></div>
    <div class="swal2-success-circular-line-right" style="background-color: rgb(255, 255, 255);"></div>
  </div>
</template>

<script>
import PaymentService from "../services/payment.service";

export default {
  name: "SuccessPay",
  data() {
    return {
      loading: true,
    }
  },
  mounted() {
    PaymentService.executeSuccessPay(
        this.$route.query['paymentId'],
        this.$route.query['PayerID'],
        this.$route.query['token']
    ).then(
        (response) => {
          console.log("SUCCESS PAY - response");
          console.log(response);
          this.loading = false;
        }
    )
  }
}
</script>

<style scoped>
@-webkit-keyframes swal2-show{
  0%{
    -webkit-transform:scale(.7);
    transform:scale(.7)
  }
  45%{
    -webkit-transform:scale(1.05);
    transform:scale(1.05)
  }
  80%{
    -webkit-transform:scale(.95);
    transform:scale(.95)
  }
  100%{
    -webkit-transform:scale(1);
    transform:scale(1)
  }
}
@keyframes swal2-show{
  0%{
    -webkit-transform:scale(.7);
    transform:scale(.7)
  }
  45%{
    -webkit-transform:scale(1.05);
    transform:scale(1.05)
  }
  80%{
    -webkit-transform:scale(.95);
    transform:scale(.95)
  }
  100%{
    -webkit-transform:scale(1);
    transform:scale(1)
  }
}
@-webkit-keyframes swal2-hide{
  0%{
    -webkit-transform:scale(1);
    transform:scale(1);
    opacity:1
  }
  100%{
    -webkit-transform:scale(.5);
    transform:scale(.5);
    opacity:0
  }
}
@keyframes swal2-hide{
  0%{
    -webkit-transform:scale(1);
    transform:scale(1);
    opacity:1
  }
  100%{
    -webkit-transform:scale(.5);
    transform:scale(.5);
    opacity:0
  }
}
@-webkit-keyframes swal2-animate-success-line-tip{
  0%{
    top:1.1875em;
    left:.0625em;
    width:0
  }
  54%{
    top:1.0625em;
    left:.125em;
    width:0
  }
  70%{
    top:2.1875em;
    left:-.375em;
    width:3.125em
  }
  84%{
    top:3em;
    left:1.3125em;
    width:1.0625em
  }
  100%{
    top:2.8125em;
    left:.875em;
    width:1.5625em
  }
}
@keyframes swal2-animate-success-line-tip{
  0%{
    top:1.1875em;
    left:.0625em;
    width:0
  }
  54%{
    top:1.0625em;
    left:.125em;
    width:0
  }
  70%{
    top:2.1875em;
    left:-.375em;
    width:3.125em
  }
  84%{
    top:3em;
    left:1.3125em;
    width:1.0625em
  }
  100%{
    top:2.8125em;
    left:.875em;
    width:1.5625em
  }
}
@-webkit-keyframes swal2-animate-success-line-long{
  0%{
    top:3.375em;
    right:2.875em;
    width:0
  }
  65%{
    top:3.375em;
    right:2.875em;
    width:0
  }
  84%{
    top:2.1875em;
    right:0;
    width:3.4375em
  }
  100%{
    top:2.375em;
    right:.5em;
    width:2.9375em
  }
}
@keyframes swal2-animate-success-line-long{
  0%{
    top:3.375em;
    right:2.875em;
    width:0
  }
  65%{
    top:3.375em;
    right:2.875em;
    width:0
  }
  84%{
    top:2.1875em;
    right:0;
    width:3.4375em
  }
  100%{
    top:2.375em;
    right:.5em;
    width:2.9375em
  }
}
@-webkit-keyframes swal2-rotate-success-circular-line{
  0%{
    -webkit-transform:rotate(-45deg);
    transform:rotate(-45deg)
  }
  5%{
    -webkit-transform:rotate(-45deg);
    transform:rotate(-45deg)
  }
  12%{
    -webkit-transform:rotate(-405deg);
    transform:rotate(-405deg)
  }
  100%{
    -webkit-transform:rotate(-405deg);
    transform:rotate(-405deg)
  }
}
@keyframes swal2-rotate-success-circular-line{
  0%{
    -webkit-transform:rotate(-45deg);
    transform:rotate(-45deg)
  }
  5%{
    -webkit-transform:rotate(-45deg);
    transform:rotate(-45deg)
  }
  12%{
    -webkit-transform:rotate(-405deg);
    transform:rotate(-405deg)
  }
  100%{
    -webkit-transform:rotate(-405deg);
    transform:rotate(-405deg)
  }
}
@-webkit-keyframes swal2-animate-error-x-mark{
  0%{
    margin-top:1.625em;
    -webkit-transform:scale(.4);
    transform:scale(.4);
    opacity:0
  }
  50%{
    margin-top:1.625em;
    -webkit-transform:scale(.4);
    transform:scale(.4);
    opacity:0
  }
  80%{
    margin-top:-.375em;
    -webkit-transform:scale(1.15);
    transform:scale(1.15)
  }
  100%{
    margin-top:0;
    -webkit-transform:scale(1);
    transform:scale(1);
    opacity:1
  }
}
@keyframes swal2-animate-error-x-mark{
  0%{
    margin-top:1.625em;
    -webkit-transform:scale(.4);
    transform:scale(.4);
    opacity:0
  }
  50%{
    margin-top:1.625em;
    -webkit-transform:scale(.4);
    transform:scale(.4);
    opacity:0
  }
  80%{
    margin-top:-.375em;
    -webkit-transform:scale(1.15);
    transform:scale(1.15)
  }
  100%{
    margin-top:0;
    -webkit-transform:scale(1);
    transform:scale(1);
    opacity:1
  }
}
@-webkit-keyframes swal2-animate-error-icon{
  0%{
    -webkit-transform:rotateX(100deg);
    transform:rotateX(100deg);
    opacity:0
  }
  100%{
    -webkit-transform:rotateX(0);
    transform:rotateX(0);
    opacity:1
  }
}
@keyframes swal2-animate-error-icon{
  0%{
    -webkit-transform:rotateX(100deg);
    transform:rotateX(100deg);
    opacity:0
  }
  100%{
    -webkit-transform:rotateX(0);
    transform:rotateX(0);
    opacity:1
  }
}

.swal2-popup.swal2-toast .swal2-icon{
  width:2em;
  min-width:2em;
  height:2em;
  margin:0
}

.swal2-popup.swal2-toast .swal2-icon.swal2-success .swal2-success-ring{
  width:2em;
  height:2em
}

.swal2-popup.swal2-toast .swal2-success{
  border-color:#a5dc86
}

.swal2-popup.swal2-toast .swal2-success .swal2-success-ring{
  width:2em;
  height:2em
}
.swal2-popup.swal2-toast .swal2-success .swal2-success-fix{
  top:0;
  left:.4375em;
  width:.4375em;
  height:2.6875em
}

.swal2-popup.swal2-toast .swal2-animate-success-icon .swal2-success-line-tip{
  -webkit-animation:animate-toast-success-tip .75s;
  animation:animate-toast-success-tip .75s
}
.swal2-popup.swal2-toast .swal2-animate-success-icon .swal2-success-line-long{
  -webkit-animation:animate-toast-success-long .75s;
  animation:animate-toast-success-long .75s
}
@-webkit-keyframes showSweetToast{
  0%{
    -webkit-transform:translateY(-.625em) rotateZ(2deg);
    transform:translateY(-.625em) rotateZ(2deg);
    opacity:0
  }
  33%{
    -webkit-transform:translateY(0) rotateZ(-2deg);
    transform:translateY(0) rotateZ(-2deg);
    opacity:.5
  }
  66%{
    -webkit-transform:translateY(.3125em) rotateZ(2deg);
    transform:translateY(.3125em) rotateZ(2deg);
    opacity:.7
  }
  100%{
    -webkit-transform:translateY(0) rotateZ(0);
    transform:translateY(0) rotateZ(0);
    opacity:1
  }
}
@keyframes showSweetToast{
  0%{
    -webkit-transform:translateY(-.625em) rotateZ(2deg);
    transform:translateY(-.625em) rotateZ(2deg);
    opacity:0
  }
  33%{
    -webkit-transform:translateY(0) rotateZ(-2deg);
    transform:translateY(0) rotateZ(-2deg);
    opacity:.5
  }
  66%{
    -webkit-transform:translateY(.3125em) rotateZ(2deg);
    transform:translateY(.3125em) rotateZ(2deg);
    opacity:.7
  }
  100%{
    -webkit-transform:translateY(0) rotateZ(0);
    transform:translateY(0) rotateZ(0);
    opacity:1
  }
}
@-webkit-keyframes hideSweetToast{
  0%{
    opacity:1
  }
  33%{
    opacity:.5
  }
  100%{
    -webkit-transform:rotateZ(1deg);
    transform:rotateZ(1deg);
    opacity:0
  }
}
@keyframes hideSweetToast{
  0%{
    opacity:1
  }
  33%{
    opacity:.5
  }
  100%{
    -webkit-transform:rotateZ(1deg);
    transform:rotateZ(1deg);
    opacity:0
  }
}
@-webkit-keyframes animate-toast-success-tip{
  0%{
    top:.5625em;
    left:.0625em;
    width:0
  }
  54%{
    top:.125em;
    left:.125em;
    width:0
  }
  70%{
    top:.625em;
    left:-.25em;
    width:1.625em
  }
  84%{
    top:1.0625em;
    left:.75em;
    width:.5em
  }
  100%{
    top:1.125em;
    left:.1875em;
    width:.75em
  }
}
@keyframes animate-toast-success-tip{
  0%{
    top:.5625em;
    left:.0625em;
    width:0
  }
  54%{
    top:.125em;
    left:.125em;
    width:0
  }
  70%{
    top:.625em;
    left:-.25em;
    width:1.625em
  }
  84%{
    top:1.0625em;
    left:.75em;
    width:.5em
  }
  100%{
    top:1.125em;
    left:.1875em;
    width:.75em
  }
}
@-webkit-keyframes animate-toast-success-long{
  0%{
    top:1.625em;
    right:1.375em;
    width:0
  }
  65%{
    top:1.25em;
    right:.9375em;
    width:0
  }
  84%{
    top:.9375em;
    right:0;
    width:1.125em
  }
  100%{
    top:.9375em;
    right:.1875em;
    width:1.375em
  }
}
@keyframes animate-toast-success-long{
  0%{
    top:1.625em;
    right:1.375em;
    width:0
  }
  65%{
    top:1.25em;
    right:.9375em;
    width:0
  }
  84%{
    top:.9375em;
    right:0;
    width:1.125em
  }
  100%{
    top:.9375em;
    right:.1875em;
    width:1.375em
  }
}

.swal2-popup .swal2-range input{
  width:80%
}
.swal2-popup .swal2-range output{
  width:20%;
  font-weight:600;
  text-align:center
}
.swal2-popup .swal2-range input,.swal2-popup .swal2-range output{
  height:2.625em;
  margin:1em auto;
  padding:0;
  font-size:1.125em;
  line-height:2.625em
}

.swal2-popup .swal2-checkbox label,.swal2-popup .swal2-radio label{
  margin:0 .6em;
  font-size:1.125em
}
.swal2-popup .swal2-checkbox input,.swal2-popup .swal2-radio input{
  margin:0 .4em
}

@supports (-ms-accelerator:true){
  .swal2-range input{
    width:100%!important
  }
  .swal2-range output{
    display:none
  }
}
@media all and (-ms-high-contrast:none),(-ms-high-contrast:active){
  .swal2-range input{
    width:100%!important
  }
  .swal2-range output{
    display:none
  }
}
.swal2-icon{
  position:relative;
  justify-content:center;
  width:5em;
  height:5em;
  margin:1.25em auto 1.875em;
  border:.25em solid transparent;
  border-radius:50%;
  line-height:5em;
  cursor:default;
  box-sizing:content-box;
  -webkit-user-select:none;
  -moz-user-select:none;
  -ms-user-select:none;
  user-select:none;
  zoom:normal
}

.swal2-icon.swal2-success{
  border-color:#a5dc86
}
.swal2-icon.swal2-success [class^=swal2-success-circular-line]{
  position:absolute;
  width:3.75em;
  height:7.5em;
  -webkit-transform:rotate(45deg);
  transform:rotate(45deg);
  border-radius:50%
}
.swal2-icon.swal2-success [class^=swal2-success-circular-line][class$=left]{
  top:-.4375em;
  left:-2.0635em;
  -webkit-transform:rotate(-45deg);
  transform:rotate(-45deg);
  -webkit-transform-origin:3.75em 3.75em;
  transform-origin:3.75em 3.75em;
  border-radius:7.5em 0 0 7.5em
}
.swal2-icon.swal2-success [class^=swal2-success-circular-line][class$=right]{
  top:-.6875em;
  left:1.875em;
  -webkit-transform:rotate(-45deg);
  transform:rotate(-45deg);
  -webkit-transform-origin:0 3.75em;
  transform-origin:0 3.75em;
  border-radius:0 7.5em 7.5em 0
}
.swal2-icon.swal2-success .swal2-success-ring{
  position:absolute;
  top:-.25em;
  left:-.25em;
  width:100%;
  height:100%;
  border:.25em solid rgba(165,220,134,.3);
  border-radius:50%;
  z-index:2;
  box-sizing:content-box
}
.swal2-icon.swal2-success .swal2-success-fix{
  position:absolute;
  top:.5em;
  left:1.625em;
  width:.4375em;
  height:5.625em;
  -webkit-transform:rotate(-45deg);
  transform:rotate(-45deg);
  z-index:1
}
.swal2-icon.swal2-success [class^=swal2-success-line]{
  display:block;
  position:absolute;
  height:.3125em;
  border-radius:.125em;
  background-color:#a5dc86;
  z-index:2
}
.swal2-icon.swal2-success [class^=swal2-success-line][class$=tip]{
  top:2.875em;
  left:.875em;
  width:1.5625em;
  -webkit-transform:rotate(45deg);
  transform:rotate(45deg)
}
.swal2-icon.swal2-success [class^=swal2-success-line][class$=long]{
  top:2.375em;
  right:.5em;
  width:2.9375em;
  -webkit-transform:rotate(-45deg);
  transform:rotate(-45deg)
}

.swal2-progresssteps li{
  display:inline-block;
  position:relative
}

.swal2-animate-success-icon .swal2-success-line-tip{
  -webkit-animation:swal2-animate-success-line-tip .75s;
  animation:swal2-animate-success-line-tip .75s
}
.swal2-animate-success-icon .swal2-success-line-long{
  -webkit-animation:swal2-animate-success-line-long .75s;
  animation:swal2-animate-success-line-long .75s
}
.swal2-animate-success-icon .swal2-success-circular-line-right{
  -webkit-animation:swal2-rotate-success-circular-line 4.25s ease-in;
  animation:swal2-rotate-success-circular-line 4.25s ease-in
}
.swal2-animate-error-icon{
  -webkit-animation:swal2-animate-error-icon .5s;
  animation:swal2-animate-error-icon .5s
}
.swal2-animate-error-icon .swal2-x-mark{
  -webkit-animation:swal2-animate-error-x-mark .5s;
  animation:swal2-animate-error-x-mark .5s
}
@-webkit-keyframes swal2-rotate-loading{
  0%{
    -webkit-transform:rotate(0);
    transform:rotate(0)
  }
  100%{
    -webkit-transform:rotate(360deg);
    transform:rotate(360deg)
  }
}
@keyframes swal2-rotate-loading{
  0%{
    -webkit-transform:rotate(0);
    transform:rotate(0)
  }
  100%{
    -webkit-transform:rotate(360deg);
    transform:rotate(360deg)
  }
}

</style>