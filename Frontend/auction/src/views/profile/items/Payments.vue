<!--<template>-->
<!--  <div class="border container-lg p-5 mb-5">-->
<!--    <Icon name="settings"/>-->
<!--    <h2 class="h2 mt-2">Payments</h2>-->
<!--    <div class="d-flex justify-content-end mt-3 mb-3">-->
<!--      <button class="badge badge-pill filter-button"-->
<!--              @click="changeGetBy('SEND')"-->
<!--              :class="{'badge-light' : this.getBy != 'SEND',-->
<!--              'badge-success' : this.getBy == 'SEND'}">SEND-->
<!--      </button>-->
<!--      <button class="badge badge-pill ml-2 filter-button"-->
<!--              @click="changeGetBy('RECEIVE')"-->
<!--              :class="{'badge-light' : this.getBy != 'RECEIVE',-->
<!--              'badge-success' : this.getBy == 'RECEIVE'}">RECEIVE-->
<!--      </button>-->
<!--    </div>-->
<!--&lt;!&ndash;    <div v-for="(item, index) in data" :key="index" class="card mw-100">&ndash;&gt;-->
<!--&lt;!&ndash;      <div class="card-header">&ndash;&gt;-->
<!--&lt;!&ndash;        Featured&ndash;&gt;-->
<!--&lt;!&ndash;      </div>&ndash;&gt;-->
<!--&lt;!&ndash;      <div class="card-body">&ndash;&gt;-->
<!--&lt;!&ndash;        <h5 class="card-title">&ndash;&gt;-->
<!--&lt;!&ndash;          <router-link :to="'/auction/' + item.auctionEvent.id">&ndash;&gt;-->
<!--&lt;!&ndash;            {{ item.auctionEvent.title }}&ndash;&gt;-->
<!--&lt;!&ndash;          </router-link>&ndash;&gt;-->
<!--&lt;!&ndash;        </h5>&ndash;&gt;-->
<!--&lt;!&ndash;        <p class="card-text">{{}}</p>&ndash;&gt;-->
<!--&lt;!&ndash;        <a :href="item.link" target="_blank" class="btn btn-primary">Go somewhere</a>&ndash;&gt;-->
<!--&lt;!&ndash;      </div>&ndash;&gt;-->
<!--&lt;!&ndash;    </div>&ndash;&gt;-->

<!--    <ag-grid-vue-->
<!--        style="width: 100%; height: 600px;"-->
<!--        class="ag-theme-alpine"-->
<!--        id="myGrid"-->
<!--        @grid-ready="onGridReady"-->
<!--        @cell-clicked="onCellClicked"-->
<!--        @row-editing-started="onRowEditingStarted"-->
<!--        @row-editing-stopped="onRowEditingStopped"-->
<!--        :gridOptions="gridOptions"-->
<!--        :columnDefs="columnDefs"-->
<!--        :defaultColDef="defaultColDef"-->
<!--        :animateRows="true"-->
<!--        :animateColumns="true"-->
<!--        :pagination=true-->
<!--        :rowData="rowData"-->
<!--        :modules="modules"-->
<!--        :paginationPageSize="paginationPageSize"-->
<!--        :editType="this.editType"-->
<!--        :suppressClickEdit="this.suppressClickEdit"-->
<!--    ></ag-grid-vue>-->

<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--import "@ag-grid-community/core/dist/styles/ag-grid.css";-->
<!--import "@ag-grid-community/core/dist/styles/ag-theme-alpine.css";-->
<!--import {AgGridVue} from "ag-grid-vue3";-->
<!--import Icon from '../../../components/Icon';-->
<!--import PaymentService from "@/services/payment.service";-->
<!--import AuctionService from "@/services/auction.service";-->
<!--import {ExcelExportModule} from "@ag-grid-enterprise/excel-export";-->

<!--export default {-->
<!--  name: "Payments",-->
<!--  components: {-->
<!--    Icon,-->
<!--  },-->
<!--  data() {-->
<!--    return {-->
<!--      data: [],-->
<!--      getBy: 'SEND',-->
<!--      loading: false,-->
<!--      page: 1,-->
<!--      perPage: 5,-->
<!--      gridOptions: null,-->
<!--      gridApi: null,-->
<!--      columnApi: null,-->
<!--      columnDefs: null,-->
<!--      defaultColDef: null,-->
<!--      rowData: null,-->
<!--      paginationPageSize: 5,-->
<!--      userId: this.$store.state.auth.user.userDto.id,-->
<!--    }-->
<!--  },-->
<!--  methods: {-->
<!--    actionCellRenderer(params) {-->
<!--      let eGui = document.createElement('div');-->

<!--      let editingCells = params.api.getEditingCells();-->
<!--// checks if the rowIndex matches in at least one of the editing cells-->
<!--      let isCurrentRowEditing = editingCells.some((cell) => {-->
<!--        return cell.rowIndex === params.node.rowIndex;-->
<!--      });-->

<!--      if (isCurrentRowEditing) {-->
<!--        eGui.innerHTML = `-->
<!--<button type="button" class="btn btn-success" data-action="update"> update</button>-->
<!--<button type="button" class="btn btn-secondary" data-action="cancel"> cancel</button>-->
<!--`;-->
<!--      } else {-->
<!--        eGui.innerHTML = `-->
<!--<button type="button" class="btn btn-primary" data-action="edit"> edit</button>-->
<!--<button type="button" class="btn btn-danger" data-action="delete"> delete</button>-->
<!--`;-->
<!--      }-->

<!--      return eGui;-->
<!--    },-->
<!--    creatLink(params) {-->
<!--      let link = document.createElement('router-link');-->
<!--      link.href = 'http://localhost:8081/auction/' + params.data.id;-->
<!--      link.innerText = params.data.title;-->
<!--      link.addEventListener('click', (e) => {-->
<!--        e.preventDefault();-->
<!--      });-->
<!--      return link;-->
<!--    },-->
<!--    onCellClicked(params) {-->
<!--// Handle click event for action cells-->
<!--      if (-->
<!--          params.column.colId === 'action' &&-->
<!--          params.event.target.dataset.action-->
<!--      ) {-->
<!--        let action = params.event.target.dataset.action;-->

<!--        if (action === 'edit') {-->
<!--          params.api.startEditingCell({-->
<!--            rowIndex: params.node.rowIndex,-->
<!--// gets the first columnKey-->
<!--            colKey: params.columnApi.getDisplayedCenterColumns()[0].colId,-->
<!--          });-->
<!--        }-->

<!--        if (action === 'delete') {-->
<!--          AuctionService.delete(params.data.id).then(-->
<!--              () => {-->
<!--                params.api.applyTransaction({-->
<!--                  remove: [params.node.data],-->
<!--                });-->
<!--              },-->
<!--              (error) => {-->
<!--                this.rowData = error.error;-->
<!--              }-->
<!--          );-->
<!--        }-->

<!--        if (action === 'update') {-->
<!--          AuctionService.update(params.node.data).then(-->
<!--              () => {-->
<!--                params.api.stopEditing(false);-->
<!--              },-->
<!--              (error) => {-->
<!--                console.log(error)-->
<!--                this.rowData = error.error;-->
<!--              }-->
<!--          );-->
<!--        }-->

<!--        if (action === 'cancel') {-->
<!--          console.log("NGH - CANCEL");-->

<!--          params.api.stopEditing(true);-->
<!--        }-->
<!--      }-->
<!--    },-->

<!--    onRowEditingStarted: (params) => {-->
<!--      params.api.refreshCells({-->
<!--        columns: ['action'],-->
<!--        rowNodes: [params.node],-->
<!--        force: true,-->
<!--      });-->
<!--    },-->
<!--    onRowEditingStopped: (params) => {-->
<!--      params.api.refreshCells({-->
<!--        columns: ['action'],-->
<!--        rowNodes: [params.node],-->
<!--        force: true,-->
<!--      });-->
<!--    },-->

<!--    onGridReady() {-->
<!--      AuctionService.findAll().then(-->
<!--          (response) => {-->
<!--            this.rowData = response.data;-->
<!--          },-->
<!--          (error) => {-->
<!--            this.$notify({-->
<!--              text: error.response.data.errorMessage,-->
<!--              type: 'error'-->
<!--            });-->
<!--          }-->
<!--      );-->
<!--    },-->
<!--    changeGetBy(filter) {-->
<!--      this.data = [];-->
<!--      this.getBy = filter;-->
<!--      this.loading = true;-->
<!--      if (filter == 'SEND') {-->
<!--        this.page = 1;-->
<!--        this.getSendPayments();-->
<!--      } else if (filter == 'RECEIVE') {-->
<!--        this.page = 1;-->
<!--        this.data = [];-->
<!--        this.getReceivePayments()-->
<!--      }-->
<!--      window.scrollTo(0, 0);-->
<!--    },-->
<!--    getReceivePayments() {-->
<!--      this.loading = true;-->
<!--      PaymentService.getReceiveOrderWithAuctionByUserId(this.userId, this.page, this.perPage)-->
<!--          .then(-->
<!--              (response) => {-->
<!--                this.loading = false;-->
<!--                this.data = response.data.content;-->
<!--              }-->
<!--          )-->
<!--    },-->
<!--    getSendPayments() {-->
<!--      this.loading = true;-->
<!--      PaymentService.getPaymentsWithAuctionByUserId(this.userId, this.page, this.perPage)-->
<!--          .then(-->
<!--              (response) => {-->
<!--                this.loading = false;-->
<!--                this.data = response.data.content;-->
<!--              }-->
<!--          )-->
<!--    }-->
<!--  },-->
<!--  beforeMount() {-->
<!--    this.gridOptions = {};-->
<!--    this.editType = 'fullRow';-->
<!--    this.suppressClickEdit = true;-->
<!--    this.columnDefs = [-->
<!--      {headerName: 'Id', field: 'id', sortable: true, filter: true},-->
<!--      {-->
<!--        headerName: 'Title', field: 'title', sortable: true, filter: true,-->
<!--        cellRenderer: this.creatLink-->
<!--      },-->
<!--      {headerName: 'Description', field: 'description', sortable: true, filter: true},-->
<!--      {headerName: 'Status', field: 'statusType', sortable: true, filter: true,},-->
<!--      {headerName: 'Charity Percent', field: 'charityPercent', sortable: true, filter: true},-->
<!--      {headerName: 'Start price', field: 'startPrice', sortable: true, filter: true},-->
<!--      {headerName: 'Finish price', field: 'finishPrice', sortable: true, filter: true},-->
<!--      {headerName: 'Start date', field: 'startDate', sortable: true, filter: true},-->
<!--      {headerName: 'Finish date', field: 'finishDate', sortable: true, filter: true},-->
<!--      {-->
<!--        headerName: 'action',-->
<!--        minWidth: 150,-->
<!--        cellRenderer: this.actionCellRenderer,-->
<!--        editable: false,-->
<!--        colId: 'action',-->
<!--      },-->
<!--    ];-->
<!--    this.defaultColDef = {-->
<!--      editable: true,-->
<!--    };-->
<!--  },-->
<!--  mounted() {-->
<!--    this.gridApi = this.gridOptions.api;-->
<!--    this.gridColumnApi = this.gridOptions.columnApi;-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<style scoped>-->
<!--.filter-button {-->
<!--  border: none;-->
<!--}-->

<!--.card-title a {-->
<!--  color: #0D0D0D;-->
<!--}-->
<!--</style>-->