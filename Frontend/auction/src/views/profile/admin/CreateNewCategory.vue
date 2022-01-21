<template>
  <div class="container">
    <button type="button" v-on:click="createNewSubCategory" class="btn btn-primary btn-block">
      Update
    </button>

    <ul>
      <li v-for="(item,index) in data" :key="index" class="">
        {{ item.mainCategory.categoryName }}
        <form class="">
          <div class="form-group">
            <label class="text-gray-600 font-semibold text-lg" hidden>Create new subcategory</label>
            <div
                v-for="(input, index) in subcategoryFields"
                :key="`subCategoryInput-${index}`"
                class="input wrapper flex items-center"
            >
              <div v-if="input.categoryId == item.mainCategory.id">
                <input
                    type="text"
                    v-model="input.subCategory"
                    class="h-10 rounded-lg outline-none p-2"
                    placeholder=" Enter New Subcategory"
                />
                <!--          Add Svg Icon-->
                <svg
                    @click="addField(input, subcategoryFields, item.mainCategory.id)"
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 24 24"
                    width="24"
                    height="24"
                    class="ml-2 cursor-pointer"
                >
                  <path fill="none" d="M0 0h24v24H0z"/>
                  <path
                      fill="green"
                      d="M11 11V7h2v4h4v2h-4v4h-2v-4H7v-2h4zm1 11C6.477 22 2 17.523 2 12S6.477 2 12 2s10 4.477 10 10-4.477 10-10 10zm0-2a8 8 0 1 0 0-16 8 8 0 0 0 0 16z"
                  />
                </svg>

                <!--          Remove Svg Icon-->
                <svg
                    v-show="subcategoryFields.length > 1"
                    @click="removeField(index, subcategoryFields, item.mainCategory.id)"
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 24 24"
                    width="24"
                    height="24"
                    class="ml-2 cursor-pointer"
                >
                  <path fill="none" d="M0 0h24v24H0z"/>
                  <path
                      fill="#EC4899"
                      d="M12 22C6.477 22 2 17.523 2 12S6.477 2 12 2s10 4.477 10 10-4.477 10-10 10zm0-2a8 8 0 1 0 0-16 8 8 0 0 0 0 16zm0-9.414l2.828-2.829 1.415 1.415L13.414 12l2.829 2.828-1.415 1.415L12 13.414l-2.828 2.829-1.415-1.415L10.586 12 7.757 9.172l1.415-1.415L12 10.586z"
                  />
                </svg>
              </div>
            </div>
          </div>
        </form>
        <ul>
          <li v-for="(subCategory,subCategoryIndex) in item.listSubCategories" :key="subCategoryIndex" class="">
            {{ subCategory.categoryName }}
          </li>
        </ul>
      </li>
    </ul>
  </div>
</template>

<script>
import CategoryService from '../../../services/category.service'

export default {
  name: "CreateNewCategory",
  components: {
    // Form,
    // Field,
  },
  data() {
    return {
      data: [],
      subcategoryFields: [{subCategory: "", categoryId: ""}],
    }
  },
  methods: {
    initButtons(response) {
      this.subcategoryFields = [];
      for (let i = 0; i < response.length; i++) {
        let id = response[i].mainCategory.id;
        this.subcategoryFields.push(
            {subCategory: "", categoryId: id}
        )
      }
    },
    createNewSubCategory() {
      console.log(this.subcategoryFields);
      let subCategory = [];
      for (let i = 0; i < this.subcategoryFields.length; i++) {
        if (this.subcategoryFields[i].subCategory != '' &&
            this.subcategoryFields[i].subCategory != undefined &&
            this.subcategoryFields[i].categoryId != null) {
          subCategory.push({
            name: this.subcategoryFields[i].subCategory,
            type: 'SUB_CATEGORY',
            mainCategoryId: this.subcategoryFields[i].categoryId
          });
        }
      }
      console.log(subCategory);
      CategoryService.createNewCategory(subCategory).then(
          (response) => {
            this.data = response.data;
            console.log(this.data)
            this.initButtons(response.data);
          },
          (error) => {
            this.$notify({
              text: error.response.data.errorMessage,
              type: 'error'
            })
          }
      );
    },
    addField(value, fieldType, mainCategoryId) {
      console.log(mainCategoryId);
      fieldType.push(
          {
            value: "",
            categoryId: mainCategoryId,
          }
      );
    },
    removeField(index, fieldType, mainCategoryId) {
      console.log(mainCategoryId);
      fieldType.splice(index, 1);
    },
    getCategories() {
      CategoryService.getCategoriesForCreateAuction().then(
          (response) => {
            this.data = response.data;
            console.log(response.data);
            this.initButtons(response.data);
          }
      )
    }
  },
  mounted() {
    this.getCategories();
  }
}
</script>

<style scoped>

</style>