package com.taoxue.ui.module.classification;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.base.basecommon.BaseAdapter.Base.RvViewHolder;
import com.common.base.basecommon.BaseAdapter.MultiItemTypeAdapter;
import com.common.base.basecommon.BaseAdapter.RvComAdapter;
import com.common.base.basecommon.BaseAdapter.RvCommonAdapter;
import com.common.base.basecommon.BaseAdapter.listener.InitViewCallBack;
import com.common.base.basecommon.BaseAdapter.listener.OnItemAdapterClickListener;
import com.taoxue.R;
import com.taoxue.app.DialogInterface;
import com.taoxue.base.BaseFragment;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.model.BaseItemListModel;
import com.taoxue.ui.model.ClassfiBean;
import com.taoxue.ui.module.home.NormalListViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A fragment with a Google +1 button.
 */
public class ClassificationFragment extends BaseFragment {

    @BindView(R.id.classification_lv)
    RecyclerView lvClass;

    Unbinder unbinder;
    private List<ClassfiBean> datas=new ArrayList<>();
//    int[]cols;
    MultiItemTypeAdapter adapter1;
    @Override
    protected int getLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.fragment_classification_list, null);
        return R.layout.fragment_classification;
    }
  private  class  ClassItemBean{
      int imgeid;
      List<String> titles;

      public ClassItemBean(int imgeid) {
         this.imgeid=imgeid;
         titles=new ArrayList<>();
      }

      public int getImgeid() {
          return imgeid;
      }

      public void setImgeid(int imgeid) {
          this.imgeid = imgeid;
      }

      public List<String> getTitles() {
          return titles;
      }

      public void setTitles(List<String> titles) {
          this.titles = titles;
      }
      public void addTitles(String title){
          this.titles.add(title);
      }


  }

//    private ArrayList<HashMap<String, Object>> listItem;
//
//
//    private void addGuan(int i){
//        listItem = new ArrayList<HashMap<String, Object>>();
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("guan",new ClassItemBean(guans[i],images[i]));
//        listItem.add(map);
//    }
//    private  void getdataVlayout(){
//        /**
//         * 步骤3:设置需要存放的数据
//         * */
//
//       addGuan(0);
//            for (int i = 0; i < guans1.length; i++) {
//                HashMap<String, Object> map = new HashMap<String, Object>();
//                map.put("guanList",new ClassItemBean(guans1[i],images1[i]));
//                listItem.add(map);
//            }
//       addGuan(1);
//
//            for (int i = 0; i < guans2.length; i++) {
//                HashMap<String, Object> map1 = new HashMap<String, Object>();
//                map1.put("guanList",new ClassItemBean(guans2[i],images2[i]));
//                listItem.add(map1);
//            }
//
//       addGuan(2);
//            for (int i = 0; i < guans3.length; i++) {
//                HashMap<String, Object> map1 = new HashMap<String, Object>();
//                map1.put("guanList",new ClassItemBean(guans3[i],images3[i]));
//                listItem.add(map1);
//            }
//
//    }
//private void setAdapter(){
//    /**
//     设置通栏布局
//     */
//
//    SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
//
//    // 公共属性
//    singleLayoutHelper.setItemCount(1);// 设置布局里Item个数
////    singleLayoutHelper.setPadding(0, 0, 0, 0);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
////    singleLayoutHelper.setMargin(0, 0, 0, 0);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
////    singleLayoutHelper.setBgColor(Color.GRAY);// 设置背景颜色
//    singleLayoutHelper.setAspectRatio(6);// 设置设置布局内每行布局的宽与高的比
//
//    VLayoutAdapter
//
//    Adapter_SingleLayout = new VLayoutAdapter(getActivity(), singleLayoutHelper,1, listItem) {
//        @Override
//        public int getItemViewLayoutId() {
//            return R.layout.fragment_classification_list;
//        }
//
//        @Override
//        public void bindView(View view, int position) {
//              TextView tv=(TextView) view.findViewById(R.id.classification_title_tv);
//
//            tv.setText(guans[0]);
//            ImageView iv=(ImageView) view.findViewById(R.id.classification_title_iv);
//            iv.setImageResource(images[0]);
//        }
//    };
//    /**
//     设置Grid布局
//     */
//    GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
//    // 在构造函数设置每行的网格个数
//
//    // 公共属性
//    gridLayoutHelper.setItemCount(guans1.length);// 设置布局里Item个数
//    gridLayoutHelper.setPadding(20, 0, 20, 20);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
//    gridLayoutHelper.setMargin(20, 0, 20, 20);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
//    // gridLayoutHelper.setBgColor(Color.GRAY);// 设置背景颜色
//    gridLayoutHelper.setAspectRatio(8);// 设置设置布局内每行布局的宽与高的比
//
//
//    // gridLayoutHelper特有属性
//    gridLayoutHelper.setWeights(new float[]{33, 33, 33});//设置每行中 每个网格宽度 占 每行总宽度 的比例
//    gridLayoutHelper.setVGap(10);// 控制子元素之间的垂直间距
//    gridLayoutHelper.setHGap(10);// 控制子元素之间的水平间距
//    gridLayoutHelper.setAutoExpand(false);//是否自动填充空白区域
//    gridLayoutHelper.setSpanCount(3);// 设置每行多少个网格
//    gridLayoutHelper.setMarginBottom(20);
//
//    VLayoutAdapter
//    Adapter_GridLayout  = new VLayoutAdapter(getActivity(), gridLayoutHelper,guans1.length, listItem) {
//        @Override
//        public int getItemViewLayoutId() {
//            return R.layout.classification_list_item;
//        }
//
//        @Override
//        public void bindView(View view, int position) {
//         TextView  tv=(TextView) view.findViewById(R.id.title_tv);
//            tv.setText(guans1[position]);
//        }
//    };
//
//    Adapter_GridLayout.setOnItemClickListener(new OnVLayItemClickListener() {
//        @Override
//        public void onItemClick(View view, int postion) {
//            launch(ClassificationDetailActivity.class,(ClassItemBean)listItem.get(postion).get("ClassItemBean").g);
//        }
//    });
//
//    /**
//     设置通栏布局
//     */
//
//    SingleLayoutHelper singleLayoutHelper1 = new SingleLayoutHelper();
//
//    // 公共属性
//    singleLayoutHelper1.setItemCount(1);// 设置布局里Item个数
//    singleLayoutHelper1.setPaddingTop(20);
//    singleLayoutHelper1.setBgColor(getResources().getColor(R.color.c_line));
//    singleLayoutHelper.setPadding(0, 20, 0, 0);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
////    singleLayoutHelper.setMargin(0, 0, 0, 0);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
////    singleLayoutHelper.setBgColor(Color.GRAY);// 设置背景颜色
//    singleLayoutHelper1.setAspectRatio(6);// 设置设置布局内每行布局的宽与高的比
//
//    VLayoutAdapter
//
//            Adapter_SingleLayout1 = new VLayoutAdapter(getActivity(), singleLayoutHelper1,1, listItem) {
//        @Override
//        public int getItemViewLayoutId() {
//            return R.layout.fragment_classification_list;
//        }
//
//        @Override
//        public void bindView(View view, int position) {
//            TextView tv=(TextView) view.findViewById(R.id.classification_title_tv);
//            tv.setText(guans[1]);
//            ImageView iv=(ImageView) view.findViewById(R.id.classification_title_iv);
//            iv.setImageResource(images[1]);
//        }
//    };
//
//
//    // 1. 设置Adapter列表（同时也是设置LayoutHelper列表）
//    List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
//
//    // 2. 将上述创建的Adapter对象放入到DelegateAdapter.Adapter列表里
//    adapters.add(Adapter_SingleLayout) ;
//    adapters.add(Adapter_GridLayout) ;
//    adapters.add(Adapter_SingleLayout1);
//    //
//    // 3. 创建DelegateAdapter对象 & 将layoutManager绑定到DelegateAdapter
//    DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
//
//    // 4. 将DelegateAdapter.Adapter列表绑定到DelegateAdapter
//    delegateAdapter.setAdapters(adapters);
//
//    // 5. 将delegateAdapter绑定到recyclerView
//    lvClass.setAdapter(delegateAdapter);
//
//
//
//}


    @Override
    protected void onInit() {
        HttpAdapter.getService().queryClassfication().enqueue(new OnResponseListener<BaseItemListModel<ClassfiBean>>((DialogInterface) getActivity()) {
            @Override
            protected void onSuccess(BaseItemListModel<ClassfiBean> classfiBeanBaseItemListModel) {
                if (classfiBeanBaseItemListModel.getItem().size()>0){
                    setAdapter(classfiBeanBaseItemListModel.getItem());
                }
            }
        });

    }

    protected void setAdapter(List<ClassfiBean>beans) {

        RvComAdapter rvCommonAdapter=new RvComAdapter.Builder<ClassfiBean>(getActivity(),R.layout.classification_list_item,beans)
                  .into(lvClass, new InitViewCallBack<ClassfiBean>() {
                      @Override
                      public void convert(RvViewHolder viewHolder, ClassfiBean classItemBean, int i) {
                          ImageView imageView=(ImageView) viewHolder.getConvertView().findViewById(R.id.imagetitle);
                          if (i>=5){
                              i=0;
                          }
                          imageView.setImageResource(guans[i]);
                          RecyclerView rv=(RecyclerView)viewHolder.getConvertView().findViewById(R.id.rvClassTitle);
                            RvComAdapter rv1=new RvComAdapter.Builder<String>(getActivity(),R.layout.classification_list_item_title,classItemBean.getClassify()).setOnItemAdapterClickListener(new OnItemAdapterClickListener() {
                                @Override
                                public void onItemClick(View view, RvViewHolder rvViewHolder, int i, int i1) {
                                    launch(NormalListViewActivity.class,((TextView)view.findViewById(R.id.classTitle)).getText(),ClassificationFragment.this.getClass().getSimpleName());
                                }
                            }).setLayoutManagerType(RvCommonAdapter.Builder.GRIDLAYOUTMANAGER,3)
                                    .into(rv, new InitViewCallBack<String>() {
                                @Override
                                public void convert(RvViewHolder viewHolder, String s, int i) {
                                    viewHolder.setText(R.id.classTitle,s);
                                }
                            });


                      }
                  });



//        LogUtils.D("datas--->" + datas.toString());
//        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),3);
//        gridLayoutManager.setOrientation(OrientationHelper. VERTICAL);
//        lvClass.setLayoutManager(gridLayoutManager);
//        adapter1=new MultiItemTypeAdapter(getActivity(),datas);
//        adapter1.addItemViewDelegate(1,new ItemViewDelegate<ClassItemBean>(){
//            @Override
//            public int getItemViewLayoutId() {
//                return R.layout.classification_list_item;
//            }
//
//            @Override
//            public boolean isForViewType(ClassItemBean item, int position) {
//                return !isEqualGuan(position);
//            }
//
//            @Override
//            public void convert(ViewHolder holder, ClassItemBean classItemBean, int position) {
//                holder.setText(R.id.title_tv,CommitContent.nullToSting(classItemBean.getItemName()));
////                holder.setImageResource(R.id.classification_item_iv,classItemBean.getImageResource());
//            }
//        });
//
//
//         adapter1.addItemViewDelegate(3,new ItemViewDelegate<ClassItemBean>() {
//             @Override
//             public int getItemViewLayoutId() {
//                 return R.layout.fragment_classification_list;
//             }
//             @Override
//             public boolean isForViewType(ClassItemBean item, int position) {
//                 return isEqualGuan(position);
//             }
//             @Override
//             public void convert(ViewHolder holder, ClassItemBean classItemBean, int position) {
//                 if (position==0){
//                     holder.setVisible(R.id.classification_title_epty,View.GONE);
//                 }
//                 holder.setText(R.id.classification_title_tv,CommitContent.nullToSting(classItemBean.getItemName()));
//                 holder.setImageResource(R.id.classification_title_iv,classItemBean.getImageResource());
//             }
//         });
////
//        adapter1.setOnItemAdapterClickListener(new OnItemAdapterClickListener() {
//            @Override
//            public void onItemClick(View view, ViewHolder holder, int position, int viewType) {
//                  LogUtils.D("position--->"+position+"viewType---->"+viewType);
//                if (viewType==1) {
//                    launch(ClassificationDetailActivity.class,datas.get(position).getItemName());
//                }
//            }
//        });
//   lvClass.addItemDecoration(new PaddingDecoration(getActivity()));
//    lvClass.setAdapter(adapter1);
//        WrapperUtils wu=new WrapperUtils();

//        adapter = new ClassAdapter(getActivity(), datas);
//        lvClass.setAdapter(adapter);
    }
    final  int [] guans={R.mipmap.remen,R.mipmap.age,R.mipmap.classfi,R.mipmap.boy,R.mipmap.parenting};
    final String[] guans1 = {"国学启蒙", "少儿故事", "历史", "益智"};
    final String[] guans2 = {"0-3岁", "4-6岁", "7-14岁", "14岁+"};
    final String[] guans3 = {"成长教育", "推理侦探", "寓言童话", "成语谚语", "科学普及", "音乐艺术", "语言文字", "教育体系", "幽默谜语","小说","人物","社会生活"};
    final String[] guans4={"个人提升","励志成长","艺术天地","品格培养","性格塑造","习惯养成"};
    final String[] guans5={"轻松家教","育儿一刻","睡前故事","早安故事","陪娃听","陪娃读","情绪管理","心里沟通","英语启蒙"};
    //    final  int []images={R.mipmap.changting,R.mipmap.shiping,R.mipmap.yuedu};
//     final  int []images1={R.mipmap.baby,R.mipmap.child,R.mipmap.student,R.mipmap.pubmed,R.mipmap.collegestudent,R.mipmap.marketingmanagement};
//     final  int []images2={R.mipmap.acqierement,R.mipmap.story,R.mipmap.music,R.mipmap.game,R.mipmap.cinema,R.mipmap.other};
//    final  int []images3={R.mipmap.english,R.mipmap.archaeology,R.mipmap.poet,R.mipmap.pinyin,R.mipmap.yuwen,R.mipmap.science,R.mipmap.wildplant,R.mipmap.education,R.mipmap.other};
//

//    private  boolean isEqualGuan(int position){
//    for (int i=0;i<guans.length;i++){
//        if (position>=datas.size()){
//            return  true;
//        }else {
//            if (datas.get(position).getItemName().equals(guans[i])) {
//                return true;
//            }
//        }
//    }
//    return  false;
//}



    boolean  isShowGuan1=true;
    boolean isShowGuan2=true;
    boolean isShowGuan3=true;

//    public void getdata() {
//        for (int i=0;i<guans.length;i++){
//            ClassItemBean itemBean=new ClassItemBean(guans[i]);
//             if (i==0){
//                 for (int j=0;j<guans1.length;j++){
//                     itemBean.addTitles(guans1[j]);
//                 }
//             }else if (i==1){
//                 for (int j=0;j<guans2.length;j++){
//                     itemBean.addTitles(guans2[j]);
//                 }
//             }else if (i==2){
//                 for (int j=0;j<guans3.length;j++){
//                     itemBean.addTitles(guans3[j]);
//                 }
//             }else if (i==3){
//                 for (int j=0;j<guans4.length;j++){
//                     itemBean.addTitles(guans4[j]);
//                 }
//             }else if (i==4){
//                 for (int j=0;j<guans5.length;j++){
//                     itemBean.addTitles(guans5[j]);
//                 }
//             }
//
//            datas.add(itemBean);
//        }
//
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
