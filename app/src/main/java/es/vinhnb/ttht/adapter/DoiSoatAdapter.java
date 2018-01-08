package es.vinhnb.ttht.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.es.tungnv.views.R;

import java.util.ArrayList;
import java.util.List;

import es.vinhnb.ttht.common.Common;
import es.vinhnb.ttht.view.TthtHnBaseActivity;
import es.vinhnb.ttht.view.TthtHnChiTietCtoFragment;

import static es.vinhnb.ttht.common.Common.TRANG_THAI_CHON_GUI.DA_CHON_GUI;
import static es.vinhnb.ttht.view.TthtHnChiTietCtoFragment.showChiso;

/**
 * Created by VinhNB on 11/22/2017.
 */

public class DoiSoatAdapter extends RecyclerView.Adapter<DoiSoatAdapter.ViewHolder> {

    public static Drawable xml_tththn_rectangle11_type1;
    public static Drawable xml_tththn_edittext;

    public static Drawable xml_tththn_rectangle11;
    public static Drawable xml_tththn_retangle_bottom;

    public static int tththn_button;
    public static int text_white;
    public static Drawable ic_tththn_mark;
    public static Drawable ic_tththn_unmark;

    private Context context;
    private List<DataDoiSoatAdapter> listData = new ArrayList<>();
    private OnIDataDoiSoatAdapter iIteractor;

    public DoiSoatAdapter(Context context, List<DataDoiSoatAdapter> listData
            , OnIDataDoiSoatAdapter iIteractor
    ) {
        this.context = context;
        this.listData.clear();
        this.listData = Common.cloneList(listData);
        this.iIteractor = iIteractor;

        if (xml_tththn_rectangle11_type1 == null)
            xml_tththn_rectangle11_type1 = ContextCompat.getDrawable(context, R.drawable.xml_tththn_rectangle11_type1);


        if (xml_tththn_rectangle11 == null)
            xml_tththn_rectangle11 = ContextCompat.getDrawable(context, R.drawable.xml_tththn_rectangle11);


        if (tththn_button == 0)
            tththn_button = ContextCompat.getColor(context, R.color.tththn_button);

        if (text_white == 0)
            text_white = ContextCompat.getColor(context, R.color.text_white);

        if (ic_tththn_mark == null)
            ic_tththn_mark = ContextCompat.getDrawable(context, R.drawable.ic_tththn_mark);

        if (ic_tththn_unmark == null)
            ic_tththn_unmark = ContextCompat.getDrawable(context, R.drawable.ic_tththn_unmark);

        if (xml_tththn_retangle_bottom == null)
            xml_tththn_retangle_bottom = ContextCompat.getDrawable(context, R.drawable.xml_tththn_retangle_bottom);

        if (xml_tththn_edittext == null)
            xml_tththn_edittext = ContextCompat.getDrawable(context, R.drawable.xml_tththn_edittext);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.row_tththn_upload, null);
        ViewHolder viewHolder = new ViewHolder(rowView);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            DataDoiSoatAdapter doiSoatAdapter = listData.get(position);

            holder.rlRow.setBackgroundColor(ContextCompat.getColor(context, doiSoatAdapter.TRANG_THAI_DU_LIEU.color));
            holder.tvStt.setText(String.valueOf(position + 1));

            holder.tvTenKH.setText(doiSoatAdapter.TEN_KH);
            holder.tvDiaChiKH.setText(doiSoatAdapter.DIA_CHI_HOADON);
            holder.tvSoBBan.setText(doiSoatAdapter.SO_BBAN);
            //default set button doi soat
            holder.btnSelectUpload.setVisibility(View.VISIBLE);
            holder.btnSelectUpload.setBackground(xml_tththn_rectangle11_type1);
            holder.btnSelectUpload.setTextColor(tththn_button);
            holder.btnSelectUpload.setText("CHỌN GỬI");
            holder.btnSelectUpload.setCompoundDrawablesWithIntrinsicBounds(null, null, ic_tththn_unmark, null);
            holder.btnSelectUpload.setVisibility(View.VISIBLE);
//            holder.editTreo.setText(Common.TEXT_EDIT.CHINH_SUA.content);
//            holder.editThao.setText(Common.TEXT_EDIT.CHINH_SUA.content);


            if (doiSoatAdapter.TRANG_THAI_CHON_GUI == DA_CHON_GUI) {
                holder.btnSelectUpload.setVisibility(View.VISIBLE);
                holder.btnSelectUpload.setBackground(xml_tththn_rectangle11);
                holder.btnSelectUpload.setTextColor(text_white);
                holder.btnSelectUpload.setText("ĐÃ CHỌN GỬI");
                holder.btnSelectUpload.setCompoundDrawablesWithIntrinsicBounds(null, null, ic_tththn_mark, null);
            }

            switch (doiSoatAdapter.TRANG_THAI_DU_LIEU) {

                case CHUA_TON_TAI:
                    holder.btnSelectUpload.setVisibility(View.GONE);
                    break;
                case CHUA_GHI:
                    holder.btnSelectUpload.setVisibility(View.GONE);
                    break;
                case DA_GHI:
                    holder.btnSelectUpload.setVisibility(View.VISIBLE);
                    break;
                case GUI_THAT_BAI:
                    holder.btnSelectUpload.setVisibility(View.VISIBLE);
                    break;
                case DANG_CHO_XAC_NHAN_CMIS:
                    holder.btnSelectUpload.setVisibility(View.GONE);
                    break;
                case DA_TON_TAI_GUI_TRUOC_DO:
                    holder.btnSelectUpload.setVisibility(View.GONE);
                    break;
                case DA_XAC_NHAN_TREN_CMIS:
                    holder.btnSelectUpload.setVisibility(View.GONE);
                    break;
                case HET_HIEU_LUC:
                    holder.btnSelectUpload.setVisibility(View.GONE);
                    break;
            }


            //default set text doi soat
            holder.tvDoiSoatTrangThai.setText(doiSoatAdapter.TRANG_THAI_DU_LIEU.content);
            holder.tvDoiSoatTrangThai.setBackgroundColor(ContextCompat.getColor(context, doiSoatAdapter.TRANG_THAI_DU_LIEU.color));


            String pathAnh = Common.getRecordDirectoryFolder(Common.FOLDER_NAME.FOLDER_ANH_CONG_TO.name()) + "/" + doiSoatAdapter.TEN_ANH_THAO;
            Bitmap bitmap = Common.getBitmapFromUri(pathAnh);
            if (bitmap != null)
                holder.ivDoiSoatThao.setImageBitmap(bitmap);

            showChiso(holder.viewBOChisoThao, doiSoatAdapter.LOAI_CTO_THAO.code, doiSoatAdapter.CHI_SO_THAO, Common.TRANG_THAI_DU_LIEU.DANG_CHO_XAC_NHAN_CMIS, false);

            enableChiso(holder.editThao, holder.viewBOChisoThao, listData.get(position).isEditThao);

            pathAnh = Common.getRecordDirectoryFolder(Common.FOLDER_NAME.FOLDER_ANH_CONG_TO.name()) + "/" + doiSoatAdapter.TEN_ANH_TREO;
            bitmap = Common.getBitmapFromUri(pathAnh);
            if (bitmap != null)
                holder.ivDoiSoatTreo.setImageBitmap(bitmap);

            showChiso(holder.viewBOChisoTreo, doiSoatAdapter.LOAI_CTO_TREO.code, doiSoatAdapter.CHI_SO_TREO, Common.TRANG_THAI_DU_LIEU.DANG_CHO_XAC_NHAN_CMIS, false);
            enableChiso(holder.editTreo, holder.viewBOChisoTreo, listData.get(position).isEditTreo);
        } catch (Exception e) {
            e.printStackTrace();
            ((TthtHnBaseActivity) context).showSnackBar(Common.MESSAGE.ex04.getContent(), e.getMessage(), null);
        }

    }

    private void enableChiso(Button editChiSo, TthtHnChiTietCtoFragment.ViewBO_CHISO viewBOChiso, boolean isEditCS) {
        viewBOChiso.etCS1.setEnabled(isEditCS);
        viewBOChiso.etCS2.setEnabled(isEditCS);
        viewBOChiso.etCS3.setEnabled(isEditCS);
        viewBOChiso.etCS4.setEnabled(isEditCS);
        viewBOChiso.etCS5.setEnabled(isEditCS);
        editChiSo.setText(isEditCS? Common.TEXT_EDIT.OK.content: Common.TEXT_EDIT.CHINH_SUA.content);
        viewBOChiso.etCS1.setBackground(isEditCS?xml_tththn_edittext:xml_tththn_retangle_bottom);
        viewBOChiso.etCS2.setBackground(isEditCS?xml_tththn_edittext:xml_tththn_retangle_bottom);
        viewBOChiso.etCS3.setBackground(isEditCS?xml_tththn_edittext:xml_tththn_retangle_bottom);
        viewBOChiso.etCS4.setBackground(isEditCS?xml_tththn_edittext:xml_tththn_retangle_bottom);
        viewBOChiso.etCS5.setBackground(isEditCS?xml_tththn_edittext:xml_tththn_retangle_bottom);
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }


    public void refresh(List<DataDoiSoatAdapter> listData) {
        this.listData.clear();
        this.listData = Common.cloneList(listData);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView tvDoiSoatTrangThai;
        public Button btnSelectUpload;
        public RelativeLayout rlRow;
        public TextView tvSoBBan;
        public TextView tvStt;

        public TextView tvTenKH;
        public TextView tvDiaChiKH;

        public ImageView ivDoiSoatThao;
        TthtHnChiTietCtoFragment.ViewBO_CHISO viewBOChisoThao;

        public ImageView ivDoiSoatTreo;
        TthtHnChiTietCtoFragment.ViewBO_CHISO viewBOChisoTreo;

        public Button editTreo;
        public Button editThao;

        public ViewHolder(View itemView) {
            super(itemView);

            rlRow = (RelativeLayout) itemView.findViewById(R.id.rl_left);
            tvSoBBan = (TextView) itemView.findViewById(R.id.tv_so_bban_doisoat);


            tvStt = (TextView) itemView.findViewById(R.id.tv_stt_upload);
            tvTenKH = (TextView) itemView.findViewById(R.id.tv_dl_tenKH);
            tvDiaChiKH = (TextView) itemView.findViewById(R.id.tv_diachi_tenKH);


            ivDoiSoatThao = (ImageView) itemView.findViewById(R.id.iv_doisoat_cto_thao);

            editTreo = (Button) itemView.findViewById(R.id.btn_edit_doisoat_treo);
            editThao = (Button) itemView.findViewById(R.id.btn_edit_doisoat_thao);


            viewBOChisoThao = new TthtHnChiTietCtoFragment.ViewBO_CHISO();
            viewBOChisoThao.tvCS1 = (TextView) itemView.findViewById(R.id.tv_41a_doisoat_CS1_thao);
            viewBOChisoThao.tvCS2 = (TextView) itemView.findViewById(R.id.tv_42a_doisoat_CS2_thao);
            viewBOChisoThao.tvCS3 = (TextView) itemView.findViewById(R.id.tv_43a_doisoat_CS3_thao);
            viewBOChisoThao.tvCS4 = (TextView) itemView.findViewById(R.id.tv_44a_doisoat_CS4_thao);
            viewBOChisoThao.tvCS5 = (TextView) itemView.findViewById(R.id.tv_45a_doisoat_CS5_thao);


            viewBOChisoThao.etCS1 = (EditText) itemView.findViewById(R.id.et_41b_doisoat_CS1_thao);
            viewBOChisoThao.etCS2 = (EditText) itemView.findViewById(R.id.et_42b_doisoat_CS2_thao);
            viewBOChisoThao.etCS3 = (EditText) itemView.findViewById(R.id.et_43b_doisoat_CS3_thao);
            viewBOChisoThao.etCS4 = (EditText) itemView.findViewById(R.id.et_44b_doisoat_CS4_thao);
            viewBOChisoThao.etCS5 = (EditText) itemView.findViewById(R.id.et_45b_doisoat_CS5_thao);


            ivDoiSoatTreo = (ImageView) itemView.findViewById(R.id.iv_doisoat_cto_treo);


            viewBOChisoTreo = new TthtHnChiTietCtoFragment.ViewBO_CHISO();
            viewBOChisoTreo.tvCS1 = (TextView) itemView.findViewById(R.id.tv_41a_doisoat_CS1_treo);
            viewBOChisoTreo.tvCS2 = (TextView) itemView.findViewById(R.id.tv_42a_doisoat_CS2_treo);
            viewBOChisoTreo.tvCS3 = (TextView) itemView.findViewById(R.id.tv_43a_doisoat_CS3_treo);
            viewBOChisoTreo.tvCS4 = (TextView) itemView.findViewById(R.id.tv_44a_doisoat_CS4_treo);
            viewBOChisoTreo.tvCS5 = (TextView) itemView.findViewById(R.id.tv_45a_doisoat_CS5_treo);


            viewBOChisoTreo.etCS1 = (EditText) itemView.findViewById(R.id.et_41b_doisoat_CS1_treo);
            viewBOChisoTreo.etCS2 = (EditText) itemView.findViewById(R.id.et_42b_doisoat_CS2_treo);
            viewBOChisoTreo.etCS3 = (EditText) itemView.findViewById(R.id.et_43b_doisoat_CS3_treo);
            viewBOChisoTreo.etCS4 = (EditText) itemView.findViewById(R.id.et_44b_doisoat_CS4_treo);
            viewBOChisoTreo.etCS5 = (EditText) itemView.findViewById(R.id.et_45b_doisoat_CS5_treo);

            tvDoiSoatTrangThai = (TextView) itemView.findViewById(R.id.tv_doisoat_trangthai);
            btnSelectUpload = (Button) itemView.findViewById(R.id.btn_doisoat_chon);

            editTreo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (!listData.get(pos).isEditTreo) {
                        viewBOChisoTreo.etCS1.requestFocus();
                        viewBOChisoTreo.etCS1.setEnabled(true);
                        viewBOChisoTreo.etCS2.setEnabled(true);
                        viewBOChisoTreo.etCS3.setEnabled(true);
                        viewBOChisoTreo.etCS4.setEnabled(true);
                        viewBOChisoTreo.etCS5.setEnabled(true);
                        editTreo.setText(Common.TEXT_EDIT.OK.content);
                        listData.get(pos).isEditTreo = true;
                        notifyDataSetChanged();
                    } else {
                        viewBOChisoTreo.etCS1.setEnabled(false);
                        viewBOChisoTreo.etCS2.setEnabled(false);
                        viewBOChisoTreo.etCS3.setEnabled(false);
                        viewBOChisoTreo.etCS4.setEnabled(false);
                        viewBOChisoTreo.etCS5.setEnabled(false);

                        //data CHI_SO
                        String etCS1Text = TextUtils.isEmpty(viewBOChisoTreo.etCS1.getText().toString()) ? "0" : viewBOChisoTreo.etCS1.getText().toString();
                        String etCS2Text = TextUtils.isEmpty(viewBOChisoTreo.etCS2.getText().toString()) ? "0" : viewBOChisoTreo.etCS2.getText().toString();
                        String etCS3Text = TextUtils.isEmpty(viewBOChisoTreo.etCS3.getText().toString()) ? "0" : viewBOChisoTreo.etCS3.getText().toString();
                        String etCS4Text = TextUtils.isEmpty(viewBOChisoTreo.etCS4.getText().toString()) ? "0" : viewBOChisoTreo.etCS4.getText().toString();
                        String etCS5Text = TextUtils.isEmpty(viewBOChisoTreo.etCS5.getText().toString()) ? "0" : viewBOChisoTreo.etCS5.getText().toString();
                        String CHI_SO = Common.getStringChiSo(etCS1Text, etCS2Text, etCS3Text, etCS4Text, etCS5Text, listData.get(pos).LOAI_CTO_TREO);
                        Bitmap bitmap = iIteractor.doClickEditCHI_SOCto(pos, listData.get(pos), CHI_SO, Common.MA_BDONG.B);
                        ivDoiSoatTreo.setImageBitmap(bitmap);
                        editTreo.setText(Common.TEXT_EDIT.CHINH_SUA.content);
                        listData.get(pos).isEditTreo = false;
                        notifyDataSetChanged();
                        return;
                    }


                }
            });

            editThao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (!listData.get(pos).isEditThao) {
                        viewBOChisoThao.etCS1.requestFocus();
                        viewBOChisoThao.etCS1.setEnabled(true);
                        viewBOChisoThao.etCS2.setEnabled(true);
                        viewBOChisoThao.etCS3.setEnabled(true);
                        viewBOChisoThao.etCS4.setEnabled(true);
                        viewBOChisoThao.etCS5.setEnabled(true);
                        editThao.setText(Common.TEXT_EDIT.OK.content);
                        listData.get(pos).isEditThao = true;
                        notifyDataSetChanged();
                    } else {
                        listData.get(pos).isEditThao = false;
                        //data CHI_SO
                        String etCS1Text = TextUtils.isEmpty(viewBOChisoThao.etCS1.getText().toString()) ? "0" : viewBOChisoThao.etCS1.getText().toString();
                        String etCS2Text = TextUtils.isEmpty(viewBOChisoThao.etCS2.getText().toString()) ? "0" : viewBOChisoThao.etCS2.getText().toString();
                        String etCS3Text = TextUtils.isEmpty(viewBOChisoThao.etCS3.getText().toString()) ? "0" : viewBOChisoThao.etCS3.getText().toString();
                        String etCS4Text = TextUtils.isEmpty(viewBOChisoThao.etCS4.getText().toString()) ? "0" : viewBOChisoThao.etCS4.getText().toString();
                        String etCS5Text = TextUtils.isEmpty(viewBOChisoThao.etCS5.getText().toString()) ? "0" : viewBOChisoThao.etCS5.getText().toString();
                        String CHI_SO = Common.getStringChiSo(etCS1Text, etCS2Text, etCS3Text, etCS4Text, etCS5Text, listData.get(pos).LOAI_CTO_THAO);
                        Bitmap bitmap = iIteractor.doClickEditCHI_SOCto(pos, listData.get(pos), CHI_SO, Common.MA_BDONG.E);
                        ivDoiSoatTreo.setImageBitmap(bitmap);
                        editThao.setText(Common.TEXT_EDIT.CHINH_SUA.content);
                        notifyDataSetChanged();
                    }

                }
            });


            ivDoiSoatTreo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        //get bitmap
                        Bitmap bitmap = (ivDoiSoatTreo.getDrawable() == null) ? null : ((BitmapDrawable) ivDoiSoatTreo.getDrawable()).getBitmap();
                        if (bitmap == null) {
                            return;
                        }


                        //zoom
                        Common.zoomImage(context, bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ((TthtHnBaseActivity) context).showSnackBar(Common.MESSAGE.ex081.getContent(), e.getMessage(), null);
                    }
                }
            });


            ivDoiSoatThao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        //get bitmap
                        Bitmap bitmap = (ivDoiSoatThao.getDrawable() == null) ? null : ((BitmapDrawable) ivDoiSoatThao.getDrawable()).getBitmap();
                        if (bitmap == null) {
                            return;
                        }


                        //zoom
                        Common.zoomImage(context, bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ((TthtHnBaseActivity) context).showSnackBar(Common.MESSAGE.ex04.getContent(), e.getMessage(), null);
                    }
                }
            });


            btnSelectUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    iIteractor.doClickChonGui(pos, listData.get(pos));

                }
            });
        }
    }

    public interface OnIDataDoiSoatAdapter {
        void doClickChonGui(int pos, DataDoiSoatAdapter dataDoiSoatAdapter);


        Bitmap doClickEditCHI_SOCto(int pos, DataDoiSoatAdapter dataDoiSoatAdapter, String CHI_SO, Common.MA_BDONG MA_BDONG);
    }

    public static class DataDoiSoatAdapter implements Cloneable {

        public String TEN_KH;
        public String DIA_CHI_HOADON;

        public String CHI_SO_TREO;
        public Common.LOAI_CTO LOAI_CTO_TREO;

        public String CHI_SO_THAO;
        public Common.LOAI_CTO LOAI_CTO_THAO;

        public String TEN_ANH_TREO;
        public String TEN_ANH_THAO;

        public Common.TRANG_THAI_DU_LIEU TRANG_THAI_DU_LIEU;
        public Common.TRANG_THAI_CHON_GUI TRANG_THAI_CHON_GUI;
        public Common.TRANG_THAI_DOI_SOAT TRANG_THAI_DOI_SOAT;
        public int ID_BBAN_TRTH;
        public String SO_BBAN;
        public boolean isEditTreo, isEditThao;

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    public static class DataDoiSoat implements Cloneable {

        public String TEN_KH;
        public String DIA_CHI_HOADON;

        public String CHI_SO;
        public Common.LOAI_CTO LOAI_CTO;

        public String TEN_ANH;

        public Common.MA_BDONG MA_BDONG;
        public int ID_BBAN_TRTH;
        public String SO_BBAN;


        public Common.TRANG_THAI_DU_LIEU TRANG_THAI_DU_LIEU;
        public Common.TRANG_THAI_CHON_GUI TRANG_THAI_CHON_GUI;
        public Common.TRANG_THAI_DOI_SOAT TRANG_THAI_DOI_SOAT;


        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }


}
