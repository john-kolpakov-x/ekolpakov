import {ActionContext} from 'vuex';
import {getStoreAccessors} from 'vuex-typescript';
import {CommentRecord} from "@/model/CommentRecord";

const {commit, read, dispatch} = getStoreAccessors<BlogPageState, State>('blog');

export interface BlogPageState {
  commentList: CommentRecord[];
  totalCount: number;
  offset: number;
  waiting: boolean;
}

export interface State {
  blogPageState: BlogPageState;
}

const TOTAL_COUNT = 1000;
const TIMEOUT = 3000;
const PAGE_SIZE = 10;

const loadTotalCount = (): Promise<number> => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(TOTAL_COUNT);
    }, TIMEOUT);
  });
};

const loadRecordList = (offset: number, count: number): Promise<CommentRecord[]> => {
  return new Promise((resolve) => {
    setTimeout(() => {
      const ret: CommentRecord[] = [];

      for (let i = offset, j = 0; i < TOTAL_COUNT && j < count; i++, j++) {
        ret.push({
          id: 'com' + i,
          author: 'me',
          text: 'Hello World ' + i,
        });
      }

      resolve(ret);
    }, TIMEOUT);
  });
};

type BlogPageContext = ActionContext<BlogPageState, State>;

const getters = {
  getOffset(state: BlogPageState): number {
    return state.offset;
  },
  getTotalCount(state: BlogPageState): number {
    return state.totalCount;
  },
  isWaiting(state: BlogPageState): boolean {
    return state.waiting;
  },
  commentList(state: BlogPageState) {
    console.log("getting commentList", state.commentList);
    return state.commentList;
  },
};

export const readIsWaiting = read(getters.isWaiting);
export const readCommentList = read(getters.commentList);

const mutations = {
  reset(state: BlogPageState) {
    state.commentList = [];
    state.totalCount = 0;
    state.offset = 0;
  },

  setTotalCount(state: BlogPageState, totalCount: number) {
    state.totalCount = totalCount;
  },

  setCommentList(state: BlogPageState, list: CommentRecord[]) {
    state.commentList = list;
    state.waiting = false;
  },

  wait(state: BlogPageState) {
    state.waiting = true;
  }
};

const actions = {
  async reset(context: BlogPageContext): Promise<void> {
    commit(mutations.reset)(context);
  },

  async loadTotalCount(context: BlogPageContext): Promise<void> {
    const totalCount: number = await loadTotalCount();
    commit(mutations.setTotalCount)(context, totalCount);
  },

  async loadPage(context: BlogPageContext): Promise<void> {
    const offset: number = read(getters.getOffset)(context);
    if (offset === 0) {
      dispatch(actions.loadTotalCount)(context);
    }

    commit(mutations.wait)(context);

    const list: CommentRecord[] = await loadRecordList(offset, PAGE_SIZE);
    commit(mutations.setCommentList)(context, list);
  },
};

export const dispatchReset = dispatch(actions.reset);
export const dispatchLoadPage = dispatch(actions.loadPage);

export const blog = {
  namespaced: true,

  state: {
    commentList: [],
    totalCount: 0,
    offset: 0,
    waiting: false,
  },

  getters: getters,
  mutations: mutations,
  actions: actions,
};


