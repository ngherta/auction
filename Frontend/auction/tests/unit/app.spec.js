import { shallowMount } from '@vue/test-utils'
import Test from '../../src/views/profile/Test'

describe('Test.vue', () => {
    it('renders props.msg when passed', () => {
        const msg = 'new message'
        const wrapper = shallowMount(Test, {
            props: { msg }
        })
        expect(wrapper.text()).toMatch(msg)
    })
})
