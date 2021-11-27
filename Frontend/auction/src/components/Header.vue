<template>
<nav class="navbar">
  <div class="container">
    <ul class="nav navbar-nav">
      <div></div>

      <li class="nav-item">
        <router-link
            class="nav-link"
            active-class="active"
            exact
            :to="{ name: 'home' }"
        >
          Home
        </router-link>
      </li>
      <li class="nav-item">
        <router-link
            class="nav-link"
            active-class="active"
            exact
            :to="{ name: 'xxx' }"
        >
          Auctions
        </router-link>
      </li>
      <li class="nav-item">
        <router-link
            class="nav-link"
            active-class="active"
            exact
            :to="{ name: 'xxx' }"
        >
          About us
        </router-link>
      </li>
      <li class="nav-item">
        <router-link
            class="nav-link"
            active-class="active"
            exact
            :to="{ name: 'xxx' }"
        >
          My profile
        </router-link>
      </li>
    </ul>
  </div>
</nav>
</template>

<script>
export default {
  name: "Header"
}
</script>

<style scoped>

</style>