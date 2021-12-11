<template>
  <ag-grid-vue
      style="width: 1000px; height: 500px"
      class="ag-theme-alpine"
      :columnDefs="columnDefs"
      :rowData="rowData"
      :animateRows="true"
      :animateColumns="true"
      :pagination=true
  >
  </ag-grid-vue>
</template>

<script>
import "ag-grid-community/dist/styles/ag-grid.css";
import "ag-grid-community/dist/styles/ag-theme-alpine.css";
import { AgGridVue } from "ag-grid-vue3";

export default {
  name: "App",
  components: {
    AgGridVue,
  },
  setup() {
    return {
      columnDefs: [
        { headerName: "Make", field: "make", sortable: true, filter: true },
        { headerName: "Model", field: "model", sortable: true, filter: true },
        { headerName: "Price", field: "price", sortable: true, filter: 'agNumberColumnFilter' },
      ],
      rowData: [
        { make: "Toyota", model: "Celica", price: 35000 },
        { make: "Ford", model: "Mondeo", price: 32000 },
        { make: "Porsche", model: "Boxter", price: 72000 },
      ],
    };
  },
};
</script>