import {shallowMount} from '@vue/test-utils'
import Test from '../../src/views/profile/Test'
import Home from "../../src/views/Home";

describe('Test.vue', () => {
    it('renders props.msg when passed', () => {
        const msg = ''
        const wrapper = shallowMount(Test, {
            props: {msg}
        })
        expect(wrapper.text()).toMatch(msg)
    })
})

describe('Home.vue Test', () => {
    it('renders message when component is created', () => {
        // render the component
        const wrapper = shallowMount(Home, {
            propsData: {
                title: 'Home'
            }
        })

        // check the name of the component
        expect(wrapper.vm.$options.name).toMatch('Home')

        // check that the title is rendered
        expect(wrapper.text()).toMatch('PreviousNext')
    })
})
