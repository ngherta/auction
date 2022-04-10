import {mount, shallowMount} from '@vue/test-utils'
import Test from '../../src/views/profile/Test'
import Home from "../../src/views/Home";
import AuctionPage from '../../src/views/AuctionPage'
import Header from '../../src/components/Header'

describe('Home.vue', () => {
    it('should format message on button click', () => {
        const wrapper = mount(Home);
        const { vm } = wrapper;
        wrapper.setData({
            name: 'Nicolae',
        });
        const button = wrapper.find('button');
        button.trigger('click');
        expect(vm.message).toBe('Hello Nicolae');
    });
    /* async keyword is needed because of updating the DOM.
    We need to wait for button click to complete and call formatMsg function.
    Otherwise the last assertion would fail */
    it('should show message on button click', async () => {
        const wrapper = mount(Home);
        const { vm } = wrapper;
        wrapper.setData({
            name: 'Adam',
            vm: vm
        });
        const button = wrapper.find('button');
        await button.trigger('click');
        const p = wrapper.find('p');
        expect(p.text()).toBe('Hello Adam');
    });
});

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

describe('AuctionPage.vue', () => {
    it('renders props.msg when passed', () => {
        const wrapper = mount(AuctionPage);
        expect(wrapper.find("button").isVisible()).toBe(false);
    })
})

describe('if logged in, show logout button', () => {
    const wrapper = mount(Header);
    wrapper.setData({ loggedIn: true });
    expect(wrapper.find("button").isVisible()).toBe(true);
});
