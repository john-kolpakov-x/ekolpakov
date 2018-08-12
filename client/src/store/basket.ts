import {ActionContext} from 'vuex';
import {getStoreAccessors} from 'vuex-typescript';

export interface Product {
  id: number;
  name: string;
  unitPrice: number;
}

export interface ProductInBasket {
  product: Product;
  isSelected: boolean;
}

export interface BasketState {
  items: ProductInBasket[];
  totalAmount: number;
}

export interface State {
  basket: BasketState;
}


type BasketContext = ActionContext<BasketState, State>;

const getters = {
  getProductNames(state: BasketState): string[] {
    return state.items.map((item) => item.product.name);
  },

  getItemsByStatus(state: BasketState) {
    return (status: boolean) => state.items.filter((item) => item.isSelected === status);
  },

  getTotalAmountWithoutDiscount(state: BasketState) {
    return state.items.reduce((total: number, item: ProductInBasket) => total + item.product.unitPrice, 0);
  },
};

const mutations = {
  reset(state: BasketState) {
    state.items = [];
    state.totalAmount = 0;
  },

  appendItem(state: BasketState, item: { product: Product; atTheEnd: boolean }) {
    state.items.push({product: item.product, isSelected: false});
  },

  setTotalAmount(state: BasketState, totalAmount: number) {
    state.totalAmount = totalAmount;
  },

  selectProducts(state: BasketState, productNames: string[]) {
    for (const productName of productNames) {
      state.items.find((item: ProductInBasket) => item.product.name === productName)!.isSelected = true;
    }
  },
};

const actions = {
  async updateTotalAmount(context: BasketContext, discount: number): Promise<number> {
    const totalBeforeDiscount: number = read(getters.getTotalAmountWithoutDiscount)(context);

    // Imagine this is a server API call to compute the discounted value:
    await new Promise((resolve) => setTimeout(() => resolve(), 500));
    const totalAfterDiscount = totalBeforeDiscount * discount;

    commit(mutations.setTotalAmount)(context, totalAfterDiscount);

    return totalAfterDiscount;
  },

  async selectAvailableItems(context: BasketContext): Promise<void> {
    // Imagine this is a server API call to figure out which items are available:
    await new Promise((resolve) => setTimeout(() => resolve(), 500));

    const availableProductNames = read(getters.getProductNames)(context);
    commit(mutations.selectProducts)(context, availableProductNames);
  },

  async SelectAvailablieItemsAndUpdateTotalAmount(context: BasketContext, discount: number): Promise<void> {
    await dispatch(actions.selectAvailableItems)(context);
    await dispatch(actions.updateTotalAmount)(context, discount);
  },
};

export const basket = {
  namespaced: true,

  state: {
    items: [],
    totalAmount: 0,
  },

  getters: getters,
  mutations: mutations,
  actions: actions,
};

const {commit, read, dispatch} = getStoreAccessors<BasketState, State>('basket');
