<template>
  <div v-if="show" class="tooltip-custom">
    <svg width="20px" height="20px" id="Layer_1" xmlns="http://www.w3.org/2000/svg" x="0px" y="0px"
         viewBox="0 0 512 512" style="enable-background:new 0 0 512 512;" xml:space="preserve">
<g>
	<g>
		<path d="M256,0C114.841,0,0,114.841,0,256s114.841,256,256,256s256-114.841,256-256S397.159,0,256,0z M256,492.308
			c-130.298,0-236.308-106.01-236.308-236.308S125.702,19.692,256,19.692S492.308,125.702,492.308,256S386.298,492.308,256,492.308z
			"/>
	</g>
</g>
      <g>
	<g>
		<path d="M246.154,118.154c-21.582,0-41.721,8.564-56.707,24.106c-3.774,3.913-3.664,10.144,0.255,13.918
			c3.909,3.774,10.144,3.664,13.918-0.254c11.24-11.655,26.346-18.078,42.534-18.078c32.577,0,59.077,26.501,59.077,59.077
			c0,20.275-9.447,26.712-26.601,38.394c-9.039,6.159-19.288,13.135-29.759,23.543c-28.111,27.923-19.394,59.044-10.861,71.597
			c1.899,2.792,4.971,4.289,8.106,4.289c1.885,0,3.793-0.543,5.481-1.669c4.495-3,5.707-9.1,2.745-13.624
			c-1.553-2.371-14.529-23.837,8.404-46.621c9.187-9.125,18.635-15.561,26.971-21.24c18.106-12.331,35.207-23.981,35.207-54.668
			C324.923,153.49,289.586,118.154,246.154,118.154z"/>
	</g>
</g>
      <g>
	<g>
		<path d="M245.135,364.308c-5.438,0-9.846,4.41-9.846,9.846V384c0,5.438,4.409,9.846,9.846,9.846c5.438,0,9.846-4.409,9.846-9.846
			v-9.846C254.981,368.718,250.572,364.308,245.135,364.308z"/>
	</g>
</g>
      <g>
</g>
      <g>
</g>
      <g>
</g>
      <g>
</g>
      <g>
</g>
      <g>
</g>
      <g>
</g>
      <g>
</g>
      <g>
</g>
      <g>
</g>
      <g>
</g>
      <g>
</g>
      <g>
</g>
      <g>
</g>
      <g>
</g>
</svg>
    <span class="tooltiptext">{{ text }}</span>
  </div>
</template>

<script>

export default {
  name: "Tooltip",
  props: ['text'],
  data() {
    return {
      show: true,
    }
  },
  created() {
    if (this.text == null) {
      this.show = false;
    }
  },
}
</script>

<style scoped>
.tooltip-custom {
  position: relative;
  display: inline-block;
}

/* Tooltip text */
.tooltip-custom .tooltiptext {
  visibility: hidden;
  width: 120px;
  background-color: #555;
  color: #fff;
  text-align: center;
  padding: 5px 0;
  border-radius: 6px;

  /* Position the tooltip text */
  position: absolute;
  z-index: 1;
  bottom: 125%;
  left: 50%;
  margin-left: -60px;

  /* Fade in tooltip */
  opacity: 0;
  transition: opacity 0.3s;
}

/* Tooltip arrow */
.tooltip-custom .tooltiptext::after {
  content: "";
  position: absolute;
  top: 100%;
  left: 50%;
  margin-left: -5px;
  border-width: 5px;
  border-style: solid;
  border-color: #555 transparent transparent transparent;
}

.tooltip-custom:hover .tooltiptext {
  visibility: visible;
  opacity: 1;
}
</style>