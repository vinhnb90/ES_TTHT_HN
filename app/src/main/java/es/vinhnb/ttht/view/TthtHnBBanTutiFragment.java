package es.vinhnb.ttht.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.es.tungnv.views.R;
import com.github.clans.fab.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.vinhnb.ttht.common.Common;
import es.vinhnb.ttht.common.Common.MA_BDONG;
import es.vinhnb.ttht.database.dao.TthtHnSQLDAO;
import es.vinhnb.ttht.database.table.TABLE_ANH_HIENTRUONG;
import es.vinhnb.ttht.database.table.TABLE_BBAN_CTO;
import es.vinhnb.ttht.database.table.TABLE_BBAN_TUTI;
import es.vinhnb.ttht.database.table.TABLE_CHITIET_CTO;
import es.vinhnb.ttht.database.table.TABLE_CHITIET_TUTI;
import es.vinhnb.ttht.database.table.TABLE_LOAI_CONG_TO;
import es.vinhnb.ttht.database.table.TABLE_LYDO_TREOTHAO;
import es.vinhnb.ttht.database.table.TABLE_TRAM;
import esolutions.com.esdatabaselib.baseSqlite.LazyList;
import esolutions.com.esdatabaselib.baseSqlite.SqlHelper;

import static android.content.ContentValues.TAG;
import static es.vinhnb.ttht.common.Common.DATE_TIME_TYPE.sqlite2;
import static es.vinhnb.ttht.common.Common.DATE_TIME_TYPE.type6;
import static es.vinhnb.ttht.common.Common.TYPE_IMAGE.IMAGE_MACH_NHI_THU_TUTI;
import static es.vinhnb.ttht.common.Common.TYPE_IMAGE.IMAGE_NIEM_PHONG_TUTI;
import static es.vinhnb.ttht.common.Common.TYPE_IMAGE.IMAGE_TUTI;
import static es.vinhnb.ttht.view.TthtHnBaseActivity.BUNDLE_POS;

public class TthtHnBBanTutiFragment extends TthtHnBaseFragment {

    private IOnTthtHnBBanTutiFragment mListener;
    private Unbinder unbinder;
    private TthtHnSQLDAO mSqlDAO;


    //scroll
    @BindView(R.id.scrollv_chitiet_tuti)
    ScrollView scrollViewTuti;


    //anchor
    @BindView(R.id.fab_tt_kh_bbantuti)
    FloatingActionButton fabTtKh;
    @BindView(R.id.tv_anchor_KH)
    TextView tvfabTtKh;


    @BindView(R.id.fab_tt_tu_bbantuti)
    FloatingActionButton fabTtTu;
    @BindView(R.id.tv_anchor_tuthao)
    TextView tvfabTtTu;


    @BindView(R.id.fab_tt_ti_bbantuti)
    FloatingActionButton fabTtTi;
    @BindView(R.id.tv_anchor_tithao)
    TextView tvfabTtTi;


    @BindView(R.id.fab_tt_camera_bbantuti)
    FloatingActionButton fabTtCamera;
    @BindView(R.id.tv_anchor_camera_tu)
    TextView tvfabTtCamera;


    @BindView(R.id.fab_tt_cannhap_bbanbtuti)
    FloatingActionButton fabTtCannhapsaulap;
    @BindView(R.id.tv_anchor_saulap)
    TextView tvfabTtCannhapsaulap;


    //row
    @BindView(R.id.include_tutiE)
    LinearLayout rlIncludeTuTiE;

    @BindView(R.id.bt_add_tuti_thao)
    Button btnAddTuTiE;

    @BindView(R.id.v_mark_tutiE)
    View vMarkTuTiE;

    int indexTuTi = -1;
    MA_BDONG maBdongTuTi;

    //khach hang
    @BindView(R.id.tv_2a_khachhang_tuti)
    TextView tvKHTuti;

    @BindView(R.id.tv_3a_diachi_tuti)
    TextView tvDiachiTuti;

    @BindView(R.id.autoet_4a_lydo_tuti)
    AutoCompleteTextView autoetLydoTuti;

    @BindView(R.id.ibtn_perform_lydotuti)
    ImageButton ibtnLydoTuti;

    @BindView(R.id.tv_5b_magcs_tuti)
    TextView tvMaGcsTuti;

    @BindView(R.id.tv_6b_sono_tuti)
    TextView tvSoNoTuti;


    //Thông tin TU Tháo
    @BindView(R.id.tv_9b_capdienap_tuthao)
    TextView tvCapdienapTuthao;

    @BindView(R.id.tv_10b_tysobien_tuthao)
    TextView tvTysobienTuthao;

    @BindView(R.id.tv_12b_ngaykiemdinh_tuthao)
    TextView tvNgayKiemdinhTuthao;

    @BindView(R.id.tv_13a_sotu_tuthao)
    TextView tvSoTuthao;

    @BindView(R.id.tv_14b_ccx_tuthao)
    TextView tvCCXTuthao;

    @BindView(R.id.tv_14b_nuocsx_tuthao)
    TextView tvNuocsxTuthao;

    @BindView(R.id.tv_13a_sovong_tuthao)
    TextView tvSovongTuthao;

    @BindView(R.id.tv_14b_chikiemdinh_tuthao)
    TextView tvChiKiemdinhTuthao;

    @BindView(R.id.tv_14b_chihopdauday_tuthao)
    TextView tvChihopdaudayTuthao;


    //Thông tin TU Treo
    @BindView(R.id.tv_9b_capdienap_tutreo)
    TextView tvCapdienapTutreo;

    @BindView(R.id.tv_10b_tysobien_tutreo)
    TextView tvTysobienTutreo;

    @BindView(R.id.tv_12b_ngaykiemdinh_tutreo)
    TextView tvNgayKiemdinhTutreo;

    @BindView(R.id.tv_13a_sotu_tutreo)
    TextView tvSoTutreo;

    @BindView(R.id.tv_14b_ccx_tutreo)
    TextView tvCCXTutreo;

    @BindView(R.id.tv_14b_nuocsx_tutreo)
    TextView tvNuocsxTutreo;

    @BindView(R.id.tv_13a_sovong_tutreo)
    TextView tvSovongTutreo;

    @BindView(R.id.tv_14b_chikiemdinh_tutreo)
    TextView tvChiKiemdinhTutreo;

    @BindView(R.id.tv_14b_chihopdauday_tutreo)
    TextView tvChihopdaudayTutreo;


    //Thông tin Ti thao
    @BindView(R.id.tv_9b_capdienap_tithao)
    TextView tvCapdienapTithao;

    @BindView(R.id.tv_10b_tysobien_tithao)
    TextView tvTysobienTithao;

    @BindView(R.id.tv_12b_ngaykiemdinh_tithao)
    TextView tvNgayKiemdinhTithao;

    @BindView(R.id.tv_13a_sotu_tithao)
    TextView tvSoTithao;

    @BindView(R.id.tv_14b_ccx_tithao)
    TextView tvCCXTithao;

    @BindView(R.id.tv_14b_nuocsx_tithao)
    TextView tvNuocsxTithao;

    @BindView(R.id.tv_13a_sovong_tithao)
    TextView tvSovongTithao;

    @BindView(R.id.tv_14b_chikiemdinh_tithao)
    TextView tvChiKiemdinhTithao;

    @BindView(R.id.tv_14b_chihopdauday_tithao)
    TextView tvChihopdaudayTithao;


    //Thông tin Ti treo
    @BindView(R.id.tv_9b_capdienap_titreo)
    TextView tvCapdienapTitreo;

    @BindView(R.id.tv_10b_tysobien_titreo)
    TextView tvTysobienTitreo;

    @BindView(R.id.tv_12b_ngaykiemdinh_titreo)
    TextView tvNgayKiemdinhTitreo;

    @BindView(R.id.tv_13a_sotu_titreo)
    TextView tvSoTitreo;

    @BindView(R.id.tv_14b_ccx_titreo)
    TextView tvCCXTitreo;

    @BindView(R.id.tv_14b_nuocsx_titreo)
    TextView tvNuocsxTitreo;

    @BindView(R.id.tv_13a_sovong_titreo)
    TextView tvSovongTitreo;

    @BindView(R.id.tv_14b_chikiemdinh_titreo)
    TextView tvChiKiemdinhTitreo;

    @BindView(R.id.tv_14b_chihopdauday_titreo)
    TextView tvChihopdaudayTitreo;


    //chup anh tu
    @BindView(R.id.iv_37_anh_tu)
    ImageView ivAnhTu;

    @BindView(R.id.ibtn_37_anh_tu)
    ImageView ibtnAnhTu;

    @BindView(R.id.btn_37_save_anh_tu)
    Button btnChupAnhTu;


    @BindView(R.id.iv_37_anhnhithu_tu)
    ImageView ivAnhNhiThuTu;

    @BindView(R.id.ibtn_37_anhnhithu_tu)
    ImageView ibtnAnhNhiThuTu;

    @BindView(R.id.btn_37_save_anhnhithu_tu)
    Button btnChupAnhNhiThuTu;


    @BindView(R.id.iv_37_anh_niemphong_tu)
    ImageView ivAnhNiemPhongTu;

    @BindView(R.id.ibtn_37_anh_niemphong_tu)
    ImageView ibtnAnhNiemPhongTu;

    @BindView(R.id.btn_37_save_anh_niemphong_tu)
    Button btnChupAnhNiemPhongTu;


    //chup anh ti
    @BindView(R.id.iv_37_anh_ti)
    ImageView ivAnhTi;

    @BindView(R.id.ibtn_37_anh_ti)
    ImageView ibtnAnhTi;

    @BindView(R.id.btn_37_save_anh_ti)
    Button btnChupAnhTi;


    @BindView(R.id.iv_37_anhnhithu_ti)
    ImageView ivAnhNhiThuTi;

    @BindView(R.id.ibtn_37_anhnhithu_ti)
    ImageView ibtnAnhNhiThuTi;

    @BindView(R.id.btn_37_save_anhnhithu_ti)
    Button btnChupAnhNhiThuTi;


    @BindView(R.id.iv_37_anh_niemphong_ti)
    ImageView ivAnhNiemPhongTi;

    @BindView(R.id.ibtn_37_anh_niemphong_ti)
    ImageView ibtnAnhNiemPhongTi;

    @BindView(R.id.btn_37_save_anh_niemphong_ti)
    Button btnChupAnhNiemPhongTi;


    //sau lap
    @BindView(R.id.et_9b_dongdien_saulap)
    EditText etDongdienSaulap;

    @BindView(R.id.et_9b_dienap_saulap)
    EditText etDienapSaulap;

    @BindView(R.id.et_9b_hesok_saulap)
    EditText etHesoKSaulap;

    @BindView(R.id.et_9b_ccx_saulap)
    EditText etCCXSaulap;

    @BindView(R.id.et_9b_chisotongP_saulap)
    EditText etTongPSaulap;

    @BindView(R.id.et_9b_chisotongQ_saulap)
    EditText etTongQSaulap;

    @BindView(R.id.et_9b_bt_saulap)
    EditText etBTSaulap;

    @BindView(R.id.et_9b_cd_saulap)
    EditText etCDSaulap;

    @BindView(R.id.et_9b_td_saulap)
    EditText etTDSaulap;

    @BindView(R.id.et_9b_lapquatu_saulap)
    EditText etLapquaTuSaulap;

    @BindView(R.id.et_9b_lapquati_saulap)
    EditText etLapquaTiSaulap;

    @BindView(R.id.et_9b_hesonhan_saulap)
    EditText etHesonhanSaulap;


    //bottom menu
    @BindView(R.id.ibtn_ghi_bbantuti)
    ImageButton ibtnGhiTuti;


    private IInteractionDataCommon onIDataCommom;
    private TABLE_CHITIET_CTO tableChitietCto;
    private TABLE_CHITIET_CTO tableChitietCtoB;
    private TABLE_CHITIET_CTO tableChitietCtoE;
    private TABLE_BBAN_CTO tableBbanCto;
    private TABLE_LOAI_CONG_TO tableLoaiCongTo;
    private TABLE_TRAM tableTram;
    private TABLE_BBAN_TUTI tableBbanTuti;
    private List<TABLE_CHITIET_TUTI> tutiListB;
    private List<TABLE_CHITIET_TUTI> tutiListE;
    private List<DataViewIncludeTuTi> tutiListViewB = new ArrayList<>();
    private List<DataViewIncludeTuTi> tutiListViewE = new ArrayList<>();
    private TABLE_CHITIET_TUTI tuTreo;
    private TABLE_CHITIET_TUTI tuThao;
    private TABLE_CHITIET_TUTI tiTreo;
    private TABLE_CHITIET_TUTI tiThao;
    private TABLE_ANH_HIENTRUONG anhTU;
    private TABLE_ANH_HIENTRUONG anhNhiThuTU;
    private TABLE_ANH_HIENTRUONG anhNiemPhongTU;
    private TABLE_ANH_HIENTRUONG anhNhiThuTI;
    private TABLE_ANH_HIENTRUONG anhTI;
    private TABLE_ANH_HIENTRUONG anhNiemPhongTI;
    private int pos = -1;
    private String timeFileCaptureAnhTuTi;
    private String timeFileCaptureAnhNhiThuTuTi;
    private String timeFileCaptureAnhNiemPhongTuTi;
    private String timeFileCaptureAnhTi;
    private String timeFileCaptureAnhNhiThuTi;
    private String timeFileCaptureAnhNiemPhongTi;
    private boolean isRefreshAnhTu;
    private boolean isRefreshAnhTi;
    private boolean isRefreshAnhNhiThuTu;
    private boolean isRefreshAnhNhiThuTi;
    private boolean isRefreshAnhNiemPhongTu;
    private boolean isRefreshAnhNiemPhongTi;
    private View viewRoot;
    private List<TABLE_LYDO_TREOTHAO> tableLydoTreothaos;


    public TthtHnBBanTutiFragment() {
        // Required empty public constructor
    }

    public static TthtHnBBanTutiFragment newInstance(int pos) {

        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_POS, pos);
        TthtHnBBanTutiFragment fragment = new TthtHnBBanTutiFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (this.getActivity() instanceof IInteractionDataCommon)
            this.onIDataCommom = (IInteractionDataCommon) getActivity();
        else
            throw new ClassCastException("context must be implemnet IInteractionDataCommon!");

        if (getArguments() != null) {
            pos = getArguments().getInt(BUNDLE_POS, -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        viewRoot = null;
        try {
            viewRoot = inflater.inflate(R.layout.fragment_ttht_hn_bban_tuti, container, false);
            unbinder = ButterKnife.bind(TthtHnBBanTutiFragment.this, viewRoot);
            initDataAndView(viewRoot);
            setAction(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
            ((TthtHnBaseActivity) getContext()).showSnackBar(Common.MESSAGE.ex03.getContent(), e.getMessage(), null);
        }


        return viewRoot;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == CAMERA_REQUEST_ANH_TUTI) {
                onActivityResultCapture(IMAGE_TUTI);
            }

            if (requestCode == CAMERA_REQUEST_ANH_NHITHU_TUTI) {
                onActivityResultCapture(IMAGE_MACH_NHI_THU_TUTI);
            }

            if (requestCode == CAMERA_REQUEST_ANH_NIEMPHONG_TUTI) {
                onActivityResultCapture(IMAGE_NIEM_PHONG_TUTI);
            }
        } catch (Exception e) {
            ((TthtHnBaseActivity) getActivity()).showSnackBar(Common.MESSAGE.ex08.getContent(), e.getMessage(), null);
        }
    }

    private void onActivityResultCapture(Common.TYPE_IMAGE typeImage) throws Exception {
        String MA_DVIQLY = tableBbanCto.getMA_DVIQLY();
        String MA_TRAM = tableBbanCto.getMA_TRAM();
        String SO_CTO = tableChitietCto.getSO_CTO();
        String TEN_ANH = "";
        String pathURICapturedAnh = null;

        boolean isTu = false;
        if (maBdongTuTi == MA_BDONG.E) {
            isTu = tutiListViewE.get(indexTuTi).isTu;
        } else
            isTu = tutiListViewB.get(indexTuTi).isTu;

        //scale ảnh
        switch (typeImage) {
            case IMAGE_CONG_TO:
            case IMAGE_CONG_TO_NIEM_PHONG:
                return;


            case IMAGE_TUTI:
                TEN_ANH = Common.getImageName(typeImage.code, timeFileCaptureAnhTuTi, MA_DVIQLY, MA_TRAM, onIDataCommom.getID_BBAN_TRTH(), SO_CTO);
                if (isTu)
                    pathURICapturedAnh = Common.getRecordDirectoryFolder(Common.FOLDER_NAME.FOLDER_ANH_TU.name()) + "/" + TEN_ANH;
                else
                    pathURICapturedAnh = Common.getRecordDirectoryFolder(Common.FOLDER_NAME.FOLDER_ANH_TI.name()) + "/" + TEN_ANH;
                break;
            case IMAGE_MACH_NHI_THU_TUTI:
                TEN_ANH = Common.getImageName(typeImage.code, timeFileCaptureAnhNhiThuTuTi, MA_DVIQLY, MA_TRAM, onIDataCommom.getID_BBAN_TRTH(), SO_CTO);
                if (isTu)
                    pathURICapturedAnh = Common.getRecordDirectoryFolder(Common.FOLDER_NAME.FOLDER_ANH_TU.name()) + "/" + TEN_ANH;
                else
                    pathURICapturedAnh = Common.getRecordDirectoryFolder(Common.FOLDER_NAME.FOLDER_ANH_TI.name()) + "/" + TEN_ANH;
                break;
            case IMAGE_NIEM_PHONG_TUTI:
                TEN_ANH = Common.getImageName(typeImage.code, timeFileCaptureAnhNiemPhongTuTi, MA_DVIQLY, MA_TRAM, onIDataCommom.getID_BBAN_TRTH(), SO_CTO);
                if (isTu)
                    pathURICapturedAnh = Common.getRecordDirectoryFolder(Common.FOLDER_NAME.FOLDER_ANH_TU.name()) + "/" + TEN_ANH;
                else
                    pathURICapturedAnh = Common.getRecordDirectoryFolder(Common.FOLDER_NAME.FOLDER_ANH_TI.name()) + "/" + TEN_ANH;
                break;
        }


        if (TextUtils.isEmpty(pathURICapturedAnh))
            return;
        Common.scaleImage(pathURICapturedAnh, getActivity());


        //get bitmap tu URI
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(pathURICapturedAnh, options);


        TABLE_ANH_HIENTRUONG TABLE_ANH_HIENTRUONGOld = new TABLE_ANH_HIENTRUONG();
        //set image and gắn cờ đã chụp lại ảnh
        switch (typeImage) {
            case IMAGE_CONG_TO:
            case IMAGE_CONG_TO_NIEM_PHONG:
                return;

            case IMAGE_TUTI:
                if (maBdongTuTi == MA_BDONG.B) {
                    tutiListViewB.get(indexTuTi).setBitmap(typeImage, bitmap);
                    tutiListViewB.get(indexTuTi).refreshView(tutiListViewB.get(indexTuTi).ivAnhTuti);
                    tutiListViewB.get(indexTuTi).flagChangeData = true;

                    if (tutiListViewB.get(indexTuTi).anhTuTi != null) {
                        TABLE_ANH_HIENTRUONGOld = (TABLE_ANH_HIENTRUONG) tutiListViewB.get(indexTuTi).anhTuTi.clone();
                    } else
                        tutiListViewB.get(indexTuTi).anhTuTi = new TABLE_ANH_HIENTRUONG();

                    tutiListViewB.get(indexTuTi).anhTuTi.setID_CHITIET_CTO(tableChitietCtoB.getID_CHITIET_CTO());
                    tutiListViewB.get(indexTuTi).anhTuTi.setCREATE_DAY(timeFileCaptureAnhTuTi);
                    tutiListViewB.get(indexTuTi).anhTuTi.setMA_NVIEN(onIDataCommom.getMaNVien());
                    tutiListViewB.get(indexTuTi).anhTuTi.setTYPE(IMAGE_TUTI.code);
                    tutiListViewB.get(indexTuTi).anhTuTi.setTEN_ANH(TEN_ANH);
                    tutiListViewB.get(indexTuTi).anhTuTi.setID_TABLE_ANH_HIENTRUONG((int) mSqlDAO.updateORInsertRows(TABLE_ANH_HIENTRUONG.class, TABLE_ANH_HIENTRUONGOld, tutiListViewB.get(indexTuTi).anhTuTi));

                } else {
                    tutiListViewE.get(indexTuTi).setBitmap(typeImage, bitmap);
                    tutiListViewE.get(indexTuTi).refreshView(tutiListViewE.get(indexTuTi).ivAnhTuti);
                    tutiListViewE.get(indexTuTi).flagChangeData = true;

                    if (tutiListViewE.get(indexTuTi).anhTuTi != null)
                        TABLE_ANH_HIENTRUONGOld = (TABLE_ANH_HIENTRUONG) tutiListViewE.get(indexTuTi).anhTuTi.clone();
                    else
                        tutiListViewE.get(indexTuTi).anhTuTi = new TABLE_ANH_HIENTRUONG();


                    tutiListViewE.get(indexTuTi).anhTuTi.setID_CHITIET_CTO(tableChitietCtoE.getID_CHITIET_CTO());
                    tutiListViewE.get(indexTuTi).anhTuTi.setCREATE_DAY(timeFileCaptureAnhTuTi);
                    tutiListViewE.get(indexTuTi).anhTuTi.setMA_NVIEN(onIDataCommom.getMaNVien());
                    tutiListViewE.get(indexTuTi).anhTuTi.setTYPE(IMAGE_TUTI.code);
                    tutiListViewE.get(indexTuTi).anhTuTi.setTEN_ANH(TEN_ANH);
                    tutiListViewE.get(indexTuTi).anhTuTi.setID_TABLE_ANH_HIENTRUONG((int) mSqlDAO.updateORInsertRows(TABLE_ANH_HIENTRUONG.class, TABLE_ANH_HIENTRUONGOld, tutiListViewE.get(indexTuTi).anhTuTi));
                }
                break;
            case IMAGE_MACH_NHI_THU_TUTI:
                if (maBdongTuTi == MA_BDONG.B) {
                    tutiListViewB.get(indexTuTi).setBitmap(typeImage, bitmap);
                    tutiListViewB.get(indexTuTi).refreshView(tutiListViewB.get(indexTuTi).ivAnhNhiThu);
                    tutiListViewB.get(indexTuTi).flagChangeData = true;

                    if (tutiListViewB.get(indexTuTi).anhNhiThu != null)
                        TABLE_ANH_HIENTRUONGOld = (TABLE_ANH_HIENTRUONG) tutiListViewB.get(indexTuTi).anhNhiThu.clone();
                    else
                        tutiListViewB.get(indexTuTi).anhNhiThu = new TABLE_ANH_HIENTRUONG();


                    tutiListViewB.get(indexTuTi).anhNhiThu.setID_CHITIET_CTO(tableChitietCtoB.getID_CHITIET_CTO());
                    tutiListViewB.get(indexTuTi).anhNhiThu.setCREATE_DAY(timeFileCaptureAnhNhiThuTuTi);
                    tutiListViewB.get(indexTuTi).anhNhiThu.setMA_NVIEN(onIDataCommom.getMaNVien());
                    tutiListViewB.get(indexTuTi).anhNhiThu.setTYPE(IMAGE_MACH_NHI_THU_TUTI.code);
                    tutiListViewB.get(indexTuTi).anhNhiThu.setTEN_ANH(TEN_ANH);
                    tutiListViewB.get(indexTuTi).anhNhiThu.setID_TABLE_ANH_HIENTRUONG((int) mSqlDAO.updateORInsertRows(TABLE_ANH_HIENTRUONG.class, TABLE_ANH_HIENTRUONGOld, tutiListViewB.get(indexTuTi).anhNhiThu));
//
                } else {
                    tutiListViewE.get(indexTuTi).setBitmap(typeImage, bitmap);
                    tutiListViewE.get(indexTuTi).refreshView(tutiListViewE.get(indexTuTi).ivAnhNhiThu);
                    tutiListViewE.get(indexTuTi).flagChangeData = true;

                    if (tutiListViewE.get(indexTuTi).anhNhiThu != null)
                        TABLE_ANH_HIENTRUONGOld = (TABLE_ANH_HIENTRUONG) tutiListViewE.get(indexTuTi).anhNhiThu.clone();
                    else
                        tutiListViewE.get(indexTuTi).anhNhiThu = new TABLE_ANH_HIENTRUONG();

                    tutiListViewE.get(indexTuTi).anhNhiThu.setID_CHITIET_CTO(tableChitietCtoE.getID_CHITIET_CTO());
                    tutiListViewE.get(indexTuTi).anhNhiThu.setCREATE_DAY(timeFileCaptureAnhNhiThuTuTi);
                    tutiListViewE.get(indexTuTi).anhNhiThu.setMA_NVIEN(onIDataCommom.getMaNVien());
                    tutiListViewE.get(indexTuTi).anhNhiThu.setTYPE(IMAGE_MACH_NHI_THU_TUTI.code);
                    tutiListViewE.get(indexTuTi).anhNhiThu.setTEN_ANH(TEN_ANH);
                    tutiListViewE.get(indexTuTi).anhNhiThu.setID_TABLE_ANH_HIENTRUONG((int) mSqlDAO.updateORInsertRows(TABLE_ANH_HIENTRUONG.class, TABLE_ANH_HIENTRUONGOld, tutiListViewE.get(indexTuTi).anhNhiThu));

                }
                break;
            case IMAGE_NIEM_PHONG_TUTI:
                if (maBdongTuTi == MA_BDONG.B) {
                    tutiListViewB.get(indexTuTi).setBitmap(typeImage, bitmap);
                    tutiListViewB.get(indexTuTi).refreshView(tutiListViewB.get(indexTuTi).ivAnhNiemPhong);
                    tutiListViewB.get(indexTuTi).flagChangeData = true;

                    if (tutiListViewB.get(indexTuTi).anhNiemPhong != null)
                        TABLE_ANH_HIENTRUONGOld = (TABLE_ANH_HIENTRUONG) tutiListViewB.get(indexTuTi).anhNiemPhong.clone();
                    else
                        tutiListViewB.get(indexTuTi).anhNiemPhong = new TABLE_ANH_HIENTRUONG();

                    tutiListViewB.get(indexTuTi).anhNiemPhong.setID_CHITIET_CTO(tableChitietCtoB.getID_CHITIET_CTO());
                    tutiListViewB.get(indexTuTi).anhNiemPhong.setCREATE_DAY(timeFileCaptureAnhNiemPhongTuTi);
                    tutiListViewB.get(indexTuTi).anhNiemPhong.setMA_NVIEN(onIDataCommom.getMaNVien());
                    tutiListViewB.get(indexTuTi).anhNiemPhong.setTYPE(IMAGE_NIEM_PHONG_TUTI.code);
                    tutiListViewB.get(indexTuTi).anhNiemPhong.setTEN_ANH(TEN_ANH);
                    tutiListViewB.get(indexTuTi).anhNiemPhong.setID_TABLE_ANH_HIENTRUONG((int) mSqlDAO.updateORInsertRows(TABLE_ANH_HIENTRUONG.class, TABLE_ANH_HIENTRUONGOld, tutiListViewB.get(indexTuTi).anhNiemPhong));
//
                } else {
                    tutiListViewE.get(indexTuTi).setBitmap(typeImage, bitmap);
                    tutiListViewE.get(indexTuTi).refreshView(tutiListViewE.get(indexTuTi).ivAnhNiemPhong);
                    tutiListViewE.get(indexTuTi).flagChangeData = true;

                    if (tutiListViewE.get(indexTuTi).anhNiemPhong != null)
                        TABLE_ANH_HIENTRUONGOld = (TABLE_ANH_HIENTRUONG) tutiListViewE.get(indexTuTi).anhNiemPhong.clone();
                    else
                        tutiListViewE.get(indexTuTi).anhNiemPhong = new TABLE_ANH_HIENTRUONG();

                    tutiListViewE.get(indexTuTi).anhNiemPhong.setID_CHITIET_CTO(tableChitietCtoE.getID_CHITIET_CTO());
                    tutiListViewE.get(indexTuTi).anhNiemPhong.setCREATE_DAY(timeFileCaptureAnhNiemPhongTuTi);
                    tutiListViewE.get(indexTuTi).anhNiemPhong.setMA_NVIEN(onIDataCommom.getMaNVien());
                    tutiListViewE.get(indexTuTi).anhNiemPhong.setTYPE(IMAGE_NIEM_PHONG_TUTI.code);
                    tutiListViewE.get(indexTuTi).anhNiemPhong.setTEN_ANH(TEN_ANH);
                    tutiListViewE.get(indexTuTi).anhNiemPhong.setID_TABLE_ANH_HIENTRUONG((int) mSqlDAO.updateORInsertRows(TABLE_ANH_HIENTRUONG.class, TABLE_ANH_HIENTRUONGOld, tutiListViewE.get(indexTuTi).anhNiemPhong));

                }
                break;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IOnTthtHnBBanTutiFragment) {
            mListener = (IOnTthtHnBBanTutiFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IOnTthtHnBBanTutiFragment");
        }

        //call Database access object
        try {
            mSqlDAO = new TthtHnSQLDAO(SqlHelper.getIntance().openDB(), context);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void onDetach() {
        mListener = null;

//        try {
//            SqlHelper.getIntance().closeDB();
//        } catch (Exception e) {
//            e.printStackTrace();
//            ((TthtHnBaseActivity) getContext()).showSnackBar(Common.MESSAGE.ex071.getContent(), e.getMessage(), null);
//        }
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onIDataCommom = null;
        unbinder.unbind();
    }

    //region TthtHnBaseFragment
    @Override
    public void initDataAndView(View viewRoot) throws Exception {
        fillDataBBanTuti();
    }

    private void fillDataBBanTuti() throws Exception {
        //get Data Chi tiet cong to
        String[] agrs = new String[]{String.valueOf(onIDataCommom.getID_BBAN_TRTH()), MA_BDONG.B.code, onIDataCommom.getMaNVien()};
        List<TABLE_CHITIET_CTO> tableChitietCtoList = mSqlDAO.getChiTietCongto(agrs);
        if (tableChitietCtoList.size() != 0)
            tableChitietCto = tableChitietCtoList.get(0);

        agrs = new String[]{String.valueOf(onIDataCommom.getID_BBAN_TRTH()), MA_BDONG.E.code, onIDataCommom.getMaNVien()};
        tableChitietCtoList = mSqlDAO.getChiTietCongto(agrs);
        if (tableChitietCtoList.size() != 0)
            tableChitietCtoE = tableChitietCtoList.get(0);


        //get Data bien ban
        String[] agrsBB = new String[]{String.valueOf(onIDataCommom.getID_BBAN_TRTH()), onIDataCommom.getMaNVien()};
        List<TABLE_BBAN_CTO> tableBbanCtoList = mSqlDAO.getBBan(agrsBB);
        if (tableBbanCtoList.size() != 0)
            tableBbanCto = tableBbanCtoList.get(0);


        //getInfo Chung loai
        String MA_CLOAI = tableChitietCto.getMA_CLOAI();
        String[] argsCloai = new String[]{MA_CLOAI};
        List<TABLE_LOAI_CONG_TO> tableLoaiCongToList = mSqlDAO.getLoaiCongto(argsCloai);
        if (tableLoaiCongToList.size() != 0)
            tableLoaiCongTo = tableLoaiCongToList.get(0);


        //getInfo Tram
        String MA_TRAM = tableBbanCto.getMA_TRAM();
        String[] argsTram = new String[]{MA_TRAM};
        List<TABLE_TRAM> tableTramList = mSqlDAO.getTRAM(argsTram);
        if (tableTramList.size() != 0)
            tableTram = tableTramList.get(0);


        //get Data Bban tuti
        List<TABLE_BBAN_TUTI> tableBbanTutiList = mSqlDAO.getBBanTuti(tableChitietCto.getID_BBAN_TUTI(), onIDataCommom.getMaNVien());
        if (tableBbanTutiList.size() != 0)
            tableBbanTuti = tableBbanTutiList.get(0);


        //get Data chi tiet tuti
        tutiListB = new ArrayList<>();
        tutiListE = new ArrayList<>();
        List<TABLE_CHITIET_TUTI> tableChitietTutiList = mSqlDAO.getChitietTuTi(tableChitietCto.getID_BBAN_TUTI(), onIDataCommom.getMaNVien());
        for (int i = 0; i < tableChitietTutiList.size(); i++) {
            TABLE_CHITIET_TUTI tableChitietTuti = tableChitietTutiList.get(i);

            if (tableChitietTuti.getMA_BDONG().equals(MA_BDONG.B.code)) {
                tutiListB.add(tableChitietTuti);
            } else
                tutiListE.add(tableChitietTuti);
//            //nếu là TU
//            if (tableChitietTuti.getIS_TU().equals(String.valueOf(Common.IS_TU.TU.code))) {
//                //MA_BDONG cho biết là TU Treo hay tháo
//                if (tableChitietTuti.getMA_BDONG().equals(Common.MA_BDONG.B.code))
//                    tuTreo = tableChitietTuti;
//
//                if (tableChitietTuti.getMA_BDONG().equals(Common.MA_BDONG.E.code))
//                    tuThao = tableChitietTuti;
//            }
//
//
//            //nếu là TI
//            if (tableChitietTuti.getIS_TU().equals(String.valueOf(Common.IS_TU.TI.code))) {
//                //MA_BDONG cho biết là TU Treo hay tháo
//                if (tableChitietTuti.getMA_BDONG().equals(Common.MA_BDONG.B.code))
//                    tiTreo = tableChitietTuti;
//
//                if (tableChitietTuti.getMA_BDONG().equals(Common.MA_BDONG.E.code))
//                    tiThao = tableChitietTuti;
//            }
        }

        //getInfo LyDo
        String MA_DVIQLY = onIDataCommom.getLoginData().getmMaDvi();
        String[] args = new String[]{MA_DVIQLY};
        tableLydoTreothaos = null;
        tableLydoTreothaos = mSqlDAO.getLydoTreothao(args);

        Common.TRANG_THAI_DU_LIEU TRANG_THAI_DU_LIEU = null;
        if (tableBbanTuti == null)
            TRANG_THAI_DU_LIEU = TRANG_THAI_DU_LIEU.CHUA_GHI;
        else
            TRANG_THAI_DU_LIEU = Common.TRANG_THAI_DU_LIEU.findTRANG_THAI_DU_LIEU(tableBbanTuti.getTRANG_THAI_DU_LIEU());
        fillSpinLyDo(TRANG_THAI_DU_LIEU);

        //fill KH
        fillInfoKH();


        for (int i = 0; i < tutiListB.size(); i++) {
            DataViewIncludeTuTi dataViewIncludeTuTi = new DataViewIncludeTuTi(getActivity(), listenerTuTi);
            tutiListViewB.add(dataViewIncludeTuTi);
            rlIncludeTuTiE.addView(dataViewIncludeTuTi);
        }

        for (int i = 0; i < tutiListE.size(); i++) {
            DataViewIncludeTuTi dataViewIncludeTuTi = new DataViewIncludeTuTi(getActivity(), listenerTuTi);
            tutiListViewE.add(dataViewIncludeTuTi);
        }


        viewRoot.post(new Runnable() {
            @Override
            public void run() {
                viewRoot.invalidate();
            }
        });


//        //get ảnh tu treo
//        String[] argsAnh = new String[]{onIDataCommom.getMaNVien(), String.valueOf(tuTreo.getID_BBAN_TUTI()), String.valueOf(tuTreo.getID_CHITIET_TUTI())};
//        List<TABLE_ANH_HIENTRUONG> tableAnhHientruongList = mSqlDAO.getAnhHienTruong(argsAnh, IMAGE_TUTI);
//        if (tableAnhHientruongList.size() != 0)
//            anhTU = tableAnhHientruongList.get(0);
//
//
//        //get anh nhi thu tu treo
//        tableAnhHientruongList.clear();
//        tableAnhHientruongList = mSqlDAO.getAnhHienTruong(argsAnh, Common.TYPE_IMAGE.IMAGE_MACH_NHI_THU_TUTI);
//        if (tableAnhHientruongList.size() != 0)
//            anhNhiThuTU = tableAnhHientruongList.get(0);
//
//
//        //get anh niem phong tu treo
//        tableAnhHientruongList.clear();
//        tableAnhHientruongList = mSqlDAO.getAnhHienTruong(argsAnh, Common.TYPE_IMAGE.IMAGE_NIEM_PHONG_TUTI);
//        if (tableAnhHientruongList.size() != 0)
//            anhNiemPhongTU = tableAnhHientruongList.get(0);
//
//
//        //get ảnh ti treo
//        tableAnhHientruongList.clear();
//        argsAnh = new String[]{onIDataCommom.getMaNVien(), String.valueOf(tiTreo.getID_BBAN_TUTI()), String.valueOf(tiTreo.getID_CHITIET_TUTI())};
//        tableAnhHientruongList = mSqlDAO.getAnhHienTruong(argsAnh, Common.TYPE_IMAGE.IMAGE_TI);
//        if (tableAnhHientruongList.size() != 0)
//            anhTI = tableAnhHientruongList.get(0);
//
//
//        //get anh nhi thu ti treo
//        tableAnhHientruongList.clear();
//        tableAnhHientruongList = mSqlDAO.getAnhHienTruong(argsAnh, Common.TYPE_IMAGE.IMAGE_MACH_NHI_THU_TI);
//        if (tableAnhHientruongList.size() != 0)
//            anhNhiThuTI = tableAnhHientruongList.get(0);
//
//
//        //get anh niem phong ti treo
//        tableAnhHientruongList.clear();
//        tableAnhHientruongList = mSqlDAO.getAnhHienTruong(argsAnh, Common.TYPE_IMAGE.IMAGE_NIEM_PHONG_TI);
//        if (tableAnhHientruongList.size() != 0)
//            anhNiemPhongTI = tableAnhHientruongList.get(0);


//        //fill Tu thao
//        fillInfoTuThao();
//
//
//        fillInfoTuTreo();
//
//
//        fillInfoTiThao();
//
//
//        fillInfoTiTreo();


        //fill sau lap
        //get TRANG_THAI_DU_LIEU
//        String TRANG_THAI_DU_LIEU = tableBbanTuti.getTRANG_THAI_DU_LIEU();
//        Common.TRANG_THAI_DU_LIEU trangThaiDuLieu = Common.TRANG_THAI_DU_LIEU.findTRANG_THAI_DU_LIEU(TRANG_THAI_DU_LIEU);
//        fillInfoSauLap(trangThaiDuLieu);


        //fill data ảnh tu treo
//        ivAnhTu.setImageBitmap(getAnh(tuTreo, IMAGE_TUTI));
//        btnChupAnhTu.setEnabled(true);
//        if (trangThaiDuLieu == Common.TRANG_THAI_DU_LIEU.DANG_CHO_XAC_NHAN_CMIS)
//            btnChupAnhTu.setEnabled(false);
//
//
//        //fill data ảnh tu treo nhi thứ
//        ivAnhNhiThuTu.setImageBitmap(getAnh(tuTreo, Common.TYPE_IMAGE.IMAGE_MACH_NHI_THU_TUTI));
//        btnChupAnhNhiThuTu.setEnabled(true);
//        if (trangThaiDuLieu == Common.TRANG_THAI_DU_LIEU.DANG_CHO_XAC_NHAN_CMIS)
//            btnChupAnhNhiThuTu.setEnabled(false);
//
//
//        //fill data ảnh tu treo niêm phong
//        ivAnhNiemPhongTu.setImageBitmap(getAnh(tuTreo, Common.TYPE_IMAGE.IMAGE_NIEM_PHONG_TUTI));
//        btnChupAnhNiemPhongTu.setEnabled(true);
//        if (trangThaiDuLieu == Common.TRANG_THAI_DU_LIEU.DANG_CHO_XAC_NHAN_CMIS)
//            btnChupAnhNiemPhongTu.setEnabled(false);
//
//
//        //fill data ảnh ti treo
//        ivAnhTi.setImageBitmap(getAnh(tiTreo, Common.TYPE_IMAGE.IMAGE_TI));
//        btnChupAnhTi.setEnabled(true);
//        if (trangThaiDuLieu == Common.TRANG_THAI_DU_LIEU.DANG_CHO_XAC_NHAN_CMIS)
//            btnChupAnhTi.setEnabled(false);
//
//
//        //fill data ảnh ti treo nhi thứ
//        ivAnhNhiThuTi.setImageBitmap(getAnh(tiTreo, Common.TYPE_IMAGE.IMAGE_MACH_NHI_THU_TI));
//        btnChupAnhNhiThuTi.setEnabled(true);
//        if (trangThaiDuLieu == Common.TRANG_THAI_DU_LIEU.DANG_CHO_XAC_NHAN_CMIS)
//            btnChupAnhNhiThuTi.setEnabled(false);
//
//
//        //fill data ảnh ti treo niêm phong
//        ivAnhNiemPhongTi.setImageBitmap(getAnh(tiTreo, Common.TYPE_IMAGE.IMAGE_NIEM_PHONG_TI));
//        btnChupAnhNiemPhongTi.setEnabled(true);
//        if (trangThaiDuLieu == Common.TRANG_THAI_DU_LIEU.DANG_CHO_XAC_NHAN_CMIS)
//            btnChupAnhNiemPhongTi.setEnabled(false);


    }


    private void fillInfoSauLap(Common.TRANG_THAI_DU_LIEU trangThaiDuLieu) {
        if (tableChitietCto != null) {
            etDongdienSaulap.setText(tableChitietCto.getDONG_DIEN_SAULAP_TUTI());
            etDienapSaulap.setText(tableChitietCto.getDIEN_AP_SAULAP_TUTI());
            etHesoKSaulap.setText(tableChitietCto.getHANGSO_K_SAULAP_TUTI());
            etCCXSaulap.setText(String.valueOf(tableChitietCto.getCAP_CX_SAULAP_TUTI()));


            etLapquaTuSaulap.setText(tableChitietCto.getSO_TU_SAULAP_TUTI());
            etLapquaTiSaulap.setText(tableChitietCto.getSO_TI_SAULAP_TUTI());
            etHesonhanSaulap.setText(String.valueOf(tableChitietCto.getHS_NHAN_SAULAP_TUTI()));


            //chi so
            Common.LOAI_CTO loaiCto = Common.LOAI_CTO.findLOAI_CTO(tableChitietCto.getLOAI_CTO());
            String CHI_SO = tableChitietCto.getCHI_SO_SAULAP_TUTI();
            HashMap<String, String> dataCHI_SO = Common.spilitCHI_SO(loaiCto, CHI_SO);

            etBTSaulap.setText(TextUtils.isEmpty(dataCHI_SO.get(Common.BO_CHISO.BT.code)) ? "0" : dataCHI_SO.get(Common.BO_CHISO.BT.code));
            etCDSaulap.setText(TextUtils.isEmpty(dataCHI_SO.get(Common.BO_CHISO.CD.code)) ? "0" : dataCHI_SO.get(Common.BO_CHISO.CD.code));
            etTDSaulap.setText(TextUtils.isEmpty(dataCHI_SO.get(Common.BO_CHISO.TD.code)) ? "0" : dataCHI_SO.get(Common.BO_CHISO.TD.code));
            etTongPSaulap.setText(TextUtils.isEmpty(dataCHI_SO.get(Common.BO_CHISO.SG.code)) ? "0" : dataCHI_SO.get(Common.BO_CHISO.SG.code));
            etTongQSaulap.setText(TextUtils.isEmpty(dataCHI_SO.get(Common.BO_CHISO.VC.code)) ? "0" : dataCHI_SO.get(Common.BO_CHISO.VC.code));

            //set enable

            etDongdienSaulap.setEnabled(true);
            etDienapSaulap.setEnabled(true);
            etHesoKSaulap.setEnabled(true);
            etCCXSaulap.setEnabled(true);
            etLapquaTuSaulap.setEnabled(true);
            etLapquaTiSaulap.setEnabled(true);
            etHesonhanSaulap.setEnabled(true);

            etBTSaulap.setEnabled(true);
            etCDSaulap.setEnabled(true);
            etTDSaulap.setEnabled(true);
            etTongPSaulap.setEnabled(true);
            etTongQSaulap.setEnabled(true);

            if (trangThaiDuLieu == Common.TRANG_THAI_DU_LIEU.DANG_CHO_XAC_NHAN_CMIS) {
                etDongdienSaulap.setEnabled(false);
                etDienapSaulap.setEnabled(false);
                etHesoKSaulap.setEnabled(false);
                etCCXSaulap.setEnabled(false);
                etLapquaTuSaulap.setEnabled(false);
                etLapquaTiSaulap.setEnabled(false);
                etHesonhanSaulap.setEnabled(false);

                etBTSaulap.setEnabled(false);
                etCDSaulap.setEnabled(false);
                etTDSaulap.setEnabled(false);
                etTongPSaulap.setEnabled(false);
                etTongQSaulap.setEnabled(false);
            }


            //chiso

        }
    }

    private Bitmap getAnh(TABLE_CHITIET_TUTI dataTuTi, Common.TYPE_IMAGE typeImage) {
        if (tableBbanTuti == null)
            return null;
        if (dataTuTi == null)
            return null;

        //get info ẢNH Tu
        String[] argsAnhTuTreo;
        argsAnhTuTreo = new String[]{onIDataCommom.getMaNVien(), String.valueOf(onIDataCommom.getMA_BDONG() == MA_BDONG.B ? onIDataCommom.getID_BBAN_TUTI_CTO_TREO() : onIDataCommom.getID_BBAN_TUTI_CTO_THAO()), String.valueOf(dataTuTi.getID_CHITIET_TUTI())};
        List<TABLE_ANH_HIENTRUONG> tableAnhHientruongList = mSqlDAO.getAnhHienTruong(argsAnhTuTreo, typeImage);
        TABLE_ANH_HIENTRUONG tableAnh = null;
        if (tableAnhHientruongList.size() != 0)
            tableAnh = tableAnhHientruongList.get(0);

        if (tableAnh == null) {
            Log.i(TAG, "getAnh: không có ảnh tu treo");
            return null;
        }


        String TEN_ANH = tableAnh.getTEN_ANH();
        if (TextUtils.isEmpty(TEN_ANH))
            return null;

        String folderAnh = "";
        switch (typeImage) {
            case IMAGE_CONG_TO:
            case IMAGE_CONG_TO_NIEM_PHONG:
                return null;

            case IMAGE_TUTI:
            case IMAGE_MACH_NHI_THU_TUTI:
            case IMAGE_NIEM_PHONG_TUTI:

                folderAnh = Common.FOLDER_NAME.FOLDER_ANH_TI.name();
                break;
        }

        String pathAnh = Common.getRecordDirectoryFolder(folderAnh + "/" + TEN_ANH);
        Bitmap bitmap = Common.getBitmapFromUri(pathAnh);
        if (bitmap == null)
            return null;

        return bitmap;
    }

    private void fillInfoTiTreo() {
        if (tiTreo != null) {
            tvCapdienapTitreo.setText(String.valueOf(tiTreo.getCAP_DAP()));
            tvTysobienTitreo.setText(String.valueOf(tiTreo.getTYSO_BIEN()));
            //2016-11-02T00:00:00
            tvNgayKiemdinhTitreo.setText(Common.convertDateToDate(String.valueOf(tiTreo.getNGAY_KDINH()), sqlite2, type6));
            tvSoTitreo.setText(String.valueOf(tiTreo.getSO_TU_TI()));
            tvNuocsxTitreo.setText(String.valueOf(tiTreo.getNUOC_SX()));
            tvSovongTitreo.setText(String.valueOf(tiTreo.getSO_VONG_THANH_CAI()));
            tvChiKiemdinhTitreo.setText(String.valueOf(tiTreo.getMA_CHI_KDINH()));
            tvChihopdaudayTitreo.setText(String.valueOf(tiTreo.getMA_CHI_HOP_DDAY()));
        }
    }

    private void fillInfoTiThao() {
        if (tiThao != null) {
            tvCapdienapTithao.setText(String.valueOf(tiThao.getCAP_DAP()));
            tvTysobienTithao.setText(String.valueOf(tiThao.getTYSO_BIEN()));
            //2016-11-02T00:00:00
            tvNgayKiemdinhTithao.setText(Common.convertDateToDate(String.valueOf(tiThao.getNGAY_KDINH()), sqlite2, type6));
            tvSoTithao.setText(String.valueOf(tiThao.getSO_TU_TI()));
            tvNuocsxTithao.setText(String.valueOf(tiThao.getNUOC_SX()));
            tvSovongTithao.setText(String.valueOf(tiThao.getSO_VONG_THANH_CAI()));
            tvChiKiemdinhTithao.setText(String.valueOf(tiThao.getMA_CHI_KDINH()));
            tvChihopdaudayTithao.setText(String.valueOf(tiThao.getMA_CHI_HOP_DDAY()));
        }
    }

    private void fillInfoTuTreo() {
        if (tuTreo != null) {
            tvCapdienapTutreo.setText(String.valueOf(tuTreo.getCAP_DAP()));
            tvTysobienTutreo.setText(String.valueOf(tuTreo.getTYSO_BIEN()));
            //2016-11-02T00:00:00
            tvNgayKiemdinhTutreo.setText(Common.convertDateToDate(String.valueOf(tuTreo.getNGAY_KDINH()), sqlite2, type6));
            tvSoTutreo.setText(String.valueOf(tuTreo.getSO_TU_TI()));
            tvNuocsxTutreo.setText(String.valueOf(tuTreo.getNUOC_SX()));
            tvSovongTutreo.setText(String.valueOf(tuTreo.getSO_VONG_THANH_CAI()));
            tvChiKiemdinhTutreo.setText(String.valueOf(tuTreo.getMA_CHI_KDINH()));
            tvChihopdaudayTutreo.setText(String.valueOf(tuTreo.getMA_CHI_HOP_DDAY()));
        }
    }

    private void fillInfoTuThao() {
        if (tuThao != null) {
            tvCapdienapTuthao.setText(String.valueOf(tuThao.getCAP_DAP()));
            tvTysobienTuthao.setText(String.valueOf(tuThao.getTYSO_BIEN()));
            //2016-11-02T00:00:00
            tvNgayKiemdinhTuthao.setText(Common.convertDateToDate(String.valueOf(tuThao.getNGAY_KDINH()), sqlite2, type6));
            tvSoTuthao.setText(String.valueOf(tuThao.getSO_TU_TI()));
            tvNuocsxTuthao.setText(String.valueOf(tuThao.getNUOC_SX()));
            tvSovongTuthao.setText(String.valueOf(tuThao.getSO_VONG_THANH_CAI()));
            tvChiKiemdinhTuthao.setText(String.valueOf(tuThao.getMA_CHI_KDINH()));
            tvChihopdaudayTuthao.setText(String.valueOf(tuThao.getMA_CHI_HOP_DDAY()));
        }
    }

    private void fillSpinLyDo(Common.TRANG_THAI_DU_LIEU TRANG_THAI_DU_LIEU) throws Exception {

        String MA_LDO = tableBbanCto.getMA_LDO();
        int pos = 0;
        for (int i = 0; i < tableLydoTreothaos.size(); i++) {
            if (tableLydoTreothaos.get(i).getMA_LDO().equalsIgnoreCase(MA_LDO)) {
                pos = i;
                break;
            }
        }


        //set adapter
        ArrayAdapter<TABLE_LYDO_TREOTHAO> adapterLydo = new ArrayAdapter<>(getActivity(),
                R.layout.row_tththn_spin, tableLydoTreothaos);
        adapterLydo.setDropDownViewResource(R.layout.row_tththn_spin_dropdown_select);
        autoetLydoTuti.setAdapter(adapterLydo);
        autoetLydoTuti.setText(tableLydoTreothaos.get(pos).toString());
        autoetLydoTuti.setThreshold(2);
        autoetLydoTuti.invalidate();

        //nếu đã ghi
        autoetLydoTuti.setEnabled(true);
        if (TRANG_THAI_DU_LIEU == Common.TRANG_THAI_DU_LIEU.DANG_CHO_XAC_NHAN_CMIS || TRANG_THAI_DU_LIEU == Common.TRANG_THAI_DU_LIEU.DA_XAC_NHAN_TREN_CMIS) {
            autoetLydoTuti.setEnabled(false);
            ibtnLydoTuti.setEnabled(false);
        }
    }

    private void fillInfoKH() {
        if (tableBbanCto != null) {
            tvKHTuti.setText(tableBbanCto.getTEN_KHANG());
            tvDiachiTuti.setText(tableBbanCto.getDCHI_HDON());
            tvMaGcsTuti.setText(tableBbanCto.getMA_GCS_CTO());

            tvKHTuti.setHint(tableBbanCto.getTEN_KHANG());
            tvDiachiTuti.setHint(tableBbanCto.getDCHI_HDON());
            tvMaGcsTuti.setHint(tableBbanCto.getMA_GCS_CTO());
        }

//        if(tableBbanTuti!=null){
//            autoetLydoTuti.setText(tableBbanTuti.getLY_DO_TREO_THAO());
//            autoetLydoTuti.setHint(tableBbanTuti.getLY_DO_TREO_THAO());
//        }

        if (tableChitietCto != null) {
            tvSoNoTuti.setText(tableChitietCto.getSO_CTO());

            tvSoNoTuti.setHint(tableChitietCto.getSO_CTO());
        }
    }

    @Override
    public void setAction(Bundle savedInstanceState) throws Exception {
        //click fab
        clickButton();

        clickFab();

        clickMenuBottom();


    }

    DataViewIncludeTuTi.ListenerViewInCludeTuTi listenerTuTi = new DataViewIncludeTuTi.ListenerViewInCludeTuTi() {

        @Override
        public void saveDataTuTi(int pos) {
            try {
                indexTuTi = pos;
                maBdongTuTi = MA_BDONG.E;

                String messageSave = tutiListViewE.get(indexTuTi).checkCanSave();
                if (!TextUtils.isEmpty(messageSave))
                    throw new Exception(messageSave);

                saveDataTuti(pos);
            } catch (Exception e) {
                e.printStackTrace();
                ((TthtHnBaseActivity) getActivity()).showSnackBar(Common.MESSAGE.ex08.getContent(), e.getMessage(), null);
            }
        }

        @Override
        public void clickbtnAnhTuti(boolean isTu, int pos) {
            try {
                indexTuTi = pos;
                maBdongTuTi = MA_BDONG.E;
                captureAnhTuTi(isTu);
            } catch (Exception e) {
                e.printStackTrace();
                ((TthtHnBaseActivity) getActivity()).showSnackBar(Common.MESSAGE.ex08.getContent(), e.getMessage(), null);
            }
        }

        @Override
        public void clickbtnAnhNhiThu(boolean isTu, int pos) {
            try {
                indexTuTi = pos;
                maBdongTuTi = MA_BDONG.E;
                captureAnhNhiThuTuTi(isTu);
            } catch (Exception e) {
                e.printStackTrace();
                ((TthtHnBaseActivity) getActivity()).showSnackBar(Common.MESSAGE.ex08.getContent(), e.getMessage(), null);
            }
        }

        @Override
        public void clickbtnAnhNiemPhong(boolean isTu, int pos) {
            try {
                indexTuTi = pos;
                maBdongTuTi = MA_BDONG.E;
                captureAnhNiemPhongTuTi(isTu);
            } catch (Exception e) {
                e.printStackTrace();
                ((TthtHnBaseActivity) getActivity()).showSnackBar(Common.MESSAGE.ex08.getContent(), e.getMessage(), null);
            }
        }
    };

    private void clickButton() {
        ibtnLydoTuti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoetLydoTuti.performClick();
                if (!autoetLydoTuti.getText().toString().equals(""))
                    ((ArrayAdapter<String>) autoetLydoTuti.getAdapter()).getFilter().filter(null);
                autoetLydoTuti.showDropDown();
            }
        });

        btnAddTuTiE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check save
                try {
                    if (indexTuTi != -1) {
                        String message = tutiListViewE.get(indexTuTi).checkCanSave();
                        if (!TextUtils.isEmpty(message)) {
                            scrollViewTuti.post(new Runnable() {
                                @Override
                                public void run() {
                                    tutiListViewE.get(indexTuTi).btnSaveDataTuTi.getParent().requestChildFocus(tutiListViewE.get(indexTuTi).btnSaveDataTuTi, tutiListViewE.get(indexTuTi).btnSaveDataTuTi);
                                    scrollViewTuti.scrollTo(0, tutiListViewE.get(indexTuTi).btnSaveDataTuTi.getTop());
                                    Common.runAnimationClickView(rlIncludeTuTiE, R.anim.tththn_twinking_view, 50);
                                }
                            });

                            ((TthtHnBaseActivity) getActivity()).showSnackBar(message, null, null);
                            return;
                        }
                    }

                    scrollViewTuti.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                vMarkTuTiE.getParent().requestChildFocus(vMarkTuTiE, vMarkTuTiE);
                                scrollViewTuti.scrollTo(0, vMarkTuTiE.getTop());

                                DataViewIncludeTuTi dataViewIncludeTuTi = new DataViewIncludeTuTi(getActivity(), listenerTuTi);
                                tutiListViewE.add(dataViewIncludeTuTi);
                                rlIncludeTuTiE.addView(dataViewIncludeTuTi);

                                viewRoot.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        indexTuTi = tutiListE.size();
                                        maBdongTuTi = MA_BDONG.E;
                                        viewRoot.invalidate();

                                        Common.runAnimationClickView(rlIncludeTuTiE, R.anim.tththn_twinking_view, 250);
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                ((TthtHnBaseActivity) getActivity()).showSnackBar(Common.MESSAGE.ex08.getContent(), e.getMessage(), null);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    ((TthtHnBaseActivity) getActivity()).showSnackBar(e.getMessage(), null, null);
                }

            }

        });
    }


    private void captureAnhNiemPhongTu() throws IOException {
        timeFileCaptureAnhNiemPhongTuTi = Common.getDateTimeNow(Common.DATE_TIME_TYPE.type12);
        String MA_DVIQLY = tableBbanCto.getMA_DVIQLY();
        String MA_TRAM = tableBbanCto.getMA_TRAM();
        String SO_CTO = tableChitietCto.getSO_CTO();


        final String fileName = Common.getRecordDirectoryFolder(Common.FOLDER_NAME.FOLDER_ANH_TU.name())
                + "/"
                + Common.getImageName(Common.TYPE_IMAGE.IMAGE_NIEM_PHONG_TUTI.name(), timeFileCaptureAnhNiemPhongTuTi, MA_DVIQLY, MA_TRAM, onIDataCommom.getID_BBAN_TRTH(), SO_CTO);


        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();


        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(cameraIntent, CAMERA_REQUEST_ANH_NIEMPHONG_TUTI);
    }

    private void captureAnhNhiThuTu() throws IOException {
        timeFileCaptureAnhNhiThuTuTi = Common.getDateTimeNow(Common.DATE_TIME_TYPE.type12);
        String MA_DVIQLY = tableBbanCto.getMA_DVIQLY();
        String MA_TRAM = tableBbanCto.getMA_TRAM();
        String SO_CTO = tableChitietCto.getSO_CTO();


        final String fileName = Common.getRecordDirectoryFolder(Common.FOLDER_NAME.FOLDER_ANH_TU.name())
                + "/"
                + Common.getImageName(Common.TYPE_IMAGE.IMAGE_MACH_NHI_THU_TUTI.name(), timeFileCaptureAnhNhiThuTuTi, MA_DVIQLY, MA_TRAM, onIDataCommom.getID_BBAN_TRTH(), SO_CTO);


        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();


        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(cameraIntent, CAMERA_REQUEST_ANH_NHITHU_TUTI);
    }

    private void captureAnhTuTi(boolean isTU) throws IOException {
        timeFileCaptureAnhTuTi = Common.getDateTimeNow(Common.DATE_TIME_TYPE.type12);
        String MA_DVIQLY = tableBbanCto.getMA_DVIQLY();
        String MA_TRAM = tableBbanCto.getMA_TRAM();
        String SO_CTO = tableChitietCto.getSO_CTO();


        String fileName = "";
        if (isTU)
            fileName = Common.getRecordDirectoryFolder(Common.FOLDER_NAME.FOLDER_ANH_TU.name())
                    + "/"
                    + Common.getImageName(IMAGE_TUTI.name(), timeFileCaptureAnhTuTi, MA_DVIQLY, MA_TRAM, onIDataCommom.getID_BBAN_TRTH(), SO_CTO);
        else
            fileName = Common.getRecordDirectoryFolder(Common.FOLDER_NAME.FOLDER_ANH_TI.name())
                    + "/"
                    + Common.getImageName(IMAGE_TUTI.name(), timeFileCaptureAnhTuTi, MA_DVIQLY, MA_TRAM, onIDataCommom.getID_BBAN_TRTH(), SO_CTO);
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();


        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(cameraIntent, CAMERA_REQUEST_ANH_TUTI);
    }


    private void captureAnhNiemPhongTuTi(boolean isTu) throws IOException {
        timeFileCaptureAnhNiemPhongTuTi = Common.getDateTimeNow(Common.DATE_TIME_TYPE.type12);


        String MA_DVIQLY = tableBbanCto.getMA_DVIQLY();
        String MA_TRAM = tableBbanCto.getMA_TRAM();
        String SO_CTO = tableChitietCto.getSO_CTO();


        String fileName = "";
        if (isTu)
            fileName = Common.getRecordDirectoryFolder(Common.FOLDER_NAME.FOLDER_ANH_TU.name())
                    + "/"
                    + Common.getImageName(Common.TYPE_IMAGE.IMAGE_NIEM_PHONG_TUTI.name(), timeFileCaptureAnhNiemPhongTuTi, MA_DVIQLY, MA_TRAM, onIDataCommom.getID_BBAN_TRTH(), SO_CTO);
        else
            fileName = Common.getRecordDirectoryFolder(Common.FOLDER_NAME.FOLDER_ANH_TI.name())
                    + "/"
                    + Common.getImageName(Common.TYPE_IMAGE.IMAGE_NIEM_PHONG_TUTI.name(), timeFileCaptureAnhNiemPhongTuTi, MA_DVIQLY, MA_TRAM, onIDataCommom.getID_BBAN_TRTH(), SO_CTO);

        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();


        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

        startActivityForResult(cameraIntent, CAMERA_REQUEST_ANH_NIEMPHONG_TUTI);
    }

    private void captureAnhNhiThuTuTi(boolean isTu) throws IOException {
        timeFileCaptureAnhNhiThuTuTi = Common.getDateTimeNow(Common.DATE_TIME_TYPE.type12);


        String MA_DVIQLY = tableBbanCto.getMA_DVIQLY();
        String MA_TRAM = tableBbanCto.getMA_TRAM();
        String SO_CTO = tableChitietCto.getSO_CTO();

        String fileName = "";
        if (isTu)
            fileName = Common.getRecordDirectoryFolder(Common.FOLDER_NAME.FOLDER_ANH_TU.name())
                    + "/"
                    + Common.getImageName(Common.TYPE_IMAGE.IMAGE_MACH_NHI_THU_TUTI.name(), timeFileCaptureAnhNhiThuTuTi, MA_DVIQLY, MA_TRAM, onIDataCommom.getID_BBAN_TRTH(), SO_CTO);

        else
            fileName = Common.getRecordDirectoryFolder(Common.FOLDER_NAME.FOLDER_ANH_TI.name())
                    + "/"
                    + Common.getImageName(Common.TYPE_IMAGE.IMAGE_MACH_NHI_THU_TUTI.name(), timeFileCaptureAnhNhiThuTuTi, MA_DVIQLY, MA_TRAM, onIDataCommom.getID_BBAN_TRTH(), SO_CTO);

        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();


        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

        startActivityForResult(cameraIntent, CAMERA_REQUEST_ANH_NHITHU_TUTI);
    }


    private void clickMenuBottom() {
        ibtnGhiTuti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //check Data
                    if (isFullRequireDataTuti()) {
                        saveDataTuti(pos);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ((TthtHnBaseActivity) getActivity()).showSnackBar(Common.MESSAGE.ex03.getContent(), e.getMessage(), null);
                }
            }
        });
    }

    private void saveDataTuti(int pos) throws Exception {


        Common.LOAI_CTO loaiCto = Common.LOAI_CTO.findLOAI_CTO(tableChitietCto.getLOAI_CTO());
        try {
            //nếu chưa có biên bản thì tạo biên bản TU TI
            if (tableBbanTuti == null) {
                tableBbanTuti = new TABLE_BBAN_TUTI();
                tableBbanTuti.setTEN_KHANG(tvKHTuti.getText().toString());
                tableBbanTuti.setMA_DVIQLY(tableBbanCto.getMA_DVIQLY());
                tableBbanTuti.setID_BBAN_TUTI(-1);
                tableBbanTuti.setMA_DDO(tableBbanCto.getMA_DDO());
                tableBbanTuti.setSO_BBAN("");
                tableBbanTuti.setNGAY_TRTH(tableBbanCto.getNGAY_TRTH());
                tableBbanTuti.setMA_NVIEN(tableBbanCto.getMA_NVIEN());
                tableBbanTuti.setTRANG_THAI("");
                tableBbanTuti.setTEN_KHANG(tvKHTuti.getText().toString());
                tableBbanTuti.setDCHI_HDON(tvDiachiTuti.getText().toString());
                tableBbanTuti.setDTHOAI(tableBbanCto.getDTHOAI());
                tableBbanTuti.setMA_GCS_CTO(tableBbanCto.getMA_GCS_CTO());
                tableBbanTuti.setMA_TRAM(tableBbanCto.getMA_TRAM());
                tableBbanTuti.setLY_DO_TREO_THAO(autoetLydoTuti.getText().toString());
                tableBbanTuti.setMA_KHANG(tableBbanCto.getMA_KHANG());
                tableBbanTuti.setID_BBAN_WEB_TUTI(-1);
                tableBbanTuti.setNVIEN_KCHI(tableBbanCto.getTEN_NVIEN_TREO_THAO());
                tableBbanTuti.setTRANG_THAI_DU_LIEU(Common.TRANG_THAI_DU_LIEU.DA_GHI.content);
                tableBbanTuti.setIS_BBAN_HIENTRUONG(Common.IS_BBAN_HIENTRUONG.LAP_NGOAI_HIENTRUONG.content);
                tableBbanTuti.setID_TABLE_BBAN_TUTI((int) mSqlDAO.insert(TABLE_BBAN_TUTI.class, tableBbanTuti));

            }

            if (tableBbanTuti.getID_TABLE_BBAN_TUTI() < 0)
                throw new Exception("Gặp lỗi khi tạo biên bản TU TI ngoài hiện trường!");


            //lưu ảnh TU TI
            if (maBdongTuTi == MA_BDONG.B) {
                tutiListViewB.get(pos).setBitmap(IMAGE_TUTI, saveAndGetBitmap(IMAGE_TUTI));
                tutiListViewB.get(pos).setBitmap(IMAGE_MACH_NHI_THU_TUTI, saveAndGetBitmap(IMAGE_MACH_NHI_THU_TUTI));
                tutiListViewB.get(pos).setBitmap(IMAGE_NIEM_PHONG_TUTI, saveAndGetBitmap(IMAGE_NIEM_PHONG_TUTI));

                //reset
                tutiListViewB.get(pos).flagChangeData = false;

            } else {
                tutiListViewE.get(pos).setBitmap(IMAGE_TUTI, saveAndGetBitmap(IMAGE_TUTI));
                tutiListViewE.get(pos).setBitmap(IMAGE_MACH_NHI_THU_TUTI, saveAndGetBitmap(IMAGE_MACH_NHI_THU_TUTI));
                tutiListViewE.get(pos).setBitmap(IMAGE_NIEM_PHONG_TUTI, saveAndGetBitmap(IMAGE_NIEM_PHONG_TUTI));

                //reset
                tutiListViewE.get(pos).flagChangeData = false;

            }

            //lưu dữ liệu TU TI
            if (maBdongTuTi == MA_BDONG.B) {
                if(tutiListViewB.get(pos).tuti==null)
                    tutiListViewB.get(pos).tuti=new TABLE_CHITIET_TUTI();
                TABLE_CHITIET_TUTI tutiListViewBOld = (TABLE_CHITIET_TUTI) tutiListViewB.get(pos).tuti.clone();
                tutiListViewB.get(pos).tuti.setCAP_CXAC(-1);
                tutiListViewB.get(pos).tuti.setCAP_DAP(-1);
                tutiListViewB.get(pos).tuti.setID_BBAN_TUTI(-1);
                tutiListViewB.get(pos).tuti.setID_CHITIET_TUTI(-1);
                tutiListViewB.get(pos).tuti.setIS_TU(Common.IS_TU.findIS_TU(tutiListViewB.get(pos).isTu).content);
                tutiListViewB.get(pos).tuti.setLOAI_TU_TI("");
                tutiListViewB.get(pos).tuti.setMA_BDONG(maBdongTuTi.code);
                tutiListViewB.get(pos).tuti.setMA_CHI_HOP_DDAY("");
                tutiListViewB.get(pos).tuti.setMA_CHI_KDINH("");
                tutiListViewB.get(pos).tuti.setMA_CLOAI("");
                tutiListViewB.get(pos).tuti.setMA_DVIQLY(tableBbanCto.getMA_DVIQLY());
                tutiListViewB.get(pos).tuti.setMO_TA("");

                tutiListViewB.get(pos).tuti.setSO_PHA(-1);
                tutiListViewB.get(pos).tuti.setTYSO_DAU("");
                tutiListViewB.get(pos).tuti.setMA_NUOC("");
                tutiListViewB.get(pos).tuti.setMA_HANG("");
                tutiListViewB.get(pos).tuti.setTRANG_THAI(-1);
                tutiListViewB.get(pos).tuti.setSO_TU_TI(tutiListViewB.get(pos).etSoTuTi.toString());
                tutiListViewB.get(pos).tuti.setNUOC_SX(tutiListViewB.get(pos).etNuocSX.toString());
                tutiListViewB.get(pos).tuti.setSO_TEM_KDINH("");
                tutiListViewB.get(pos).tuti.setNGAY_KDINH(tutiListViewB.get(pos).etNgayKDinh.toString());
                tutiListViewB.get(pos).tuti.setMA_CHI_KDINH("");
                tutiListViewB.get(pos).tuti.setMA_CHI_HOP_DDAY("");

                tutiListViewB.get(pos).tuti.setSO_VONG_THANH_CAI(Integer.parseInt(tutiListViewB.get(pos).etTysoVongTuTi.toString()));
                tutiListViewB.get(pos).tuti.setTYSO_BIEN(tutiListViewB.get(pos).etTysobienTuTi.toString());
                tutiListViewB.get(pos).tuti.setMA_NVIEN(tableBbanCto.getMA_NVIEN());

                tutiListViewB.get(pos).setHintAfterSave();

                tutiListViewB.get(pos).tuti.setID_CHITIET_TUTI();
                tutiListViewBOld
            }else {


                tutiListViewE.get(pos).setHintAfterSave();
            }
            tuti

        } catch (Exception e) {
            String[] collumnCheck = new String[]{
                    TABLE_BBAN_TUTI.table.ID_TABLE_BBAN_TUTI.name()
            };
            String[] valuesCheck = new String[]{
                    String.valueOf(tableBbanTuti.getID_TABLE_BBAN_TUTI())
            };

            int rowdelete = mSqlDAO.deleteRows(TABLE_BBAN_TUTI.class, collumnCheck, valuesCheck);
            tableBbanTuti = null;
            throw e;
        }


//
//        String sDongdienSaulap = etDongdienSaulap.getText().toString();
//
//        String sDienapSaulap = etDienapSaulap.getText().toString();
//
//        String sHesoKSaulap = etHesoKSaulap.getText().toString();
//
//        String sCCXSaulap = etCCXSaulap.getText().toString();
//
//
//        String sTongPSaulap = etTongPSaulap.getText().toString();
//
//        String sTongQSaulap = etTongQSaulap.getText().toString();
//
//        String sBTSaulap = etBTSaulap.getText().toString();
//
//        String sCDSaulap = etCDSaulap.getText().toString();
//
//        String sTDSaulap = etTDSaulap.getText().toString();
//
//        String CHI_SO_SAULAP_TUTI = Common.getStringChiSo(sBTSaulap, sCDSaulap, sTDSaulap, sTongPSaulap, sTongQSaulap, loaiCto);
//
//        String sLapquaTuSaulap = etLapquaTuSaulap.getText().toString();
//
//        String sLapquaTiSaulap = etLapquaTiSaulap.getText().toString();
//
//        String sHesonhanSaulap = etHesonhanSaulap.getText().toString();
//
//        //update TABLE_CHITIET_CTO
//        TABLE_CHITIET_CTO tableChitietCtoOld = (TABLE_CHITIET_CTO) tableChitietCto.clone();
//        tableChitietCto.setDONG_DIEN_SAULAP_TUTI(sDongdienSaulap);
//        tableChitietCto.setDIEN_AP_SAULAP_TUTI(sDienapSaulap);
//        tableChitietCto.setHANGSO_K_SAULAP_TUTI(sHesoKSaulap);
//        tableChitietCto.setCAP_CX_SAULAP_TUTI(Integer.parseInt(sCCXSaulap));
//        tableChitietCto.setCHI_SO_SAULAP_TUTI(CHI_SO_SAULAP_TUTI);
//        tableChitietCto.setSO_TU_SAULAP_TUTI(sLapquaTuSaulap);
//        tableChitietCto.setSO_TI_SAULAP_TUTI(sLapquaTiSaulap);
//        tableChitietCto.setHS_NHAN_SAULAP_TUTI(Integer.parseInt(sHesonhanSaulap));
//        tableChitietCto.setID_CHITIET_CTO((int) mSqlDAO.updateORInsertRows(TABLE_CHITIET_CTO.class, tableChitietCtoOld, tableChitietCto));


//        //update TABLE_BBAN_TUTI
//        TABLE_BBAN_TUTI tableBbanTutiOld = (TABLE_BBAN_TUTI) tableBbanTuti.clone();
//        tableBbanTuti.setTRANG_THAI_DU_LIEU(Common.TRANG_THAI_DU_LIEU.DA_GHI.content);
//        tableBbanTuti.setID_TABLE_BBAN_TUTI((int) mSqlDAO.updateORInsertRows(TABLE_BBAN_TUTI.class, tableBbanTutiOld, tableBbanTuti));


//        isRefreshAnhTu = false;
//        isRefreshAnhTi = false;
//        isRefreshAnhNhiThuTu = false;
//        isRefreshAnhNhiThuTi = false;
//        isRefreshAnhNiemPhongTu = false;
//        isRefreshAnhNiemPhongTi = false;

        //reset


        ((TthtHnBaseActivity) getContext()).showSnackBar("Thêm TU TI thành công!", null, null);
    }

    private Bitmap saveAndGetBitmap(Common.TYPE_IMAGE typeImage) throws Exception {
        //TODO save
        String MA_DVIQLY = tableBbanCto.getMA_DVIQLY();
        String SO_CTO = tableChitietCto.getSO_CTO();
        String TEN_KHANG = tableBbanCto.getTEN_KHANG();
        String MA_DDO = tableBbanCto.getMA_DDO();
        String MA_TRAM = tableBbanCto.getMA_TRAM();
        Common.LOAI_CTO loaiCto = Common.LOAI_CTO.findLOAI_CTO(tableChitietCto.getLOAI_CTO());
        String TYPE_IMAGE_DRAW;
        String SO_TUTI_DRAW = "";
        String MA_DDO_DRAW;
        String SO_CTO_DRAW;
        String DATE_TIME_DRAW;
        String timeNameFileCapturedAnh;
        String timeSQLSaveAnh;
        String pathOldAnh = "";

        TABLE_ANH_HIENTRUONG tableAnhHientruongOld = null;

        switch (typeImage) {
            case IMAGE_CONG_TO:
            case IMAGE_CONG_TO_NIEM_PHONG:
                return null;

            case IMAGE_TUTI:
            case IMAGE_MACH_NHI_THU_TUTI:
            case IMAGE_NIEM_PHONG_TUTI:
                if ((maBdongTuTi == MA_BDONG.B)) {
                    SO_TUTI_DRAW = (tutiListViewB.get(pos).isTu) ? "SỐ TU" : "SỐ TI" + " TREO: " + tutiListViewB.get(pos).etSoTuTi.getText().toString();
                } else
                    SO_TUTI_DRAW = (tutiListViewE.get(pos).isTu) ? "SỐ TU" : "SỐ TI" + " THÁO: " + tutiListViewE.get(pos).etSoTuTi.getText().toString();
                break;
        }


        TYPE_IMAGE_DRAW = typeImage.nameImage;
        MA_DDO_DRAW = "MÃ Đ.ĐO:" + MA_DDO;
        SO_CTO_DRAW = "SỐ C.TƠ:" + SO_CTO;
        DATE_TIME_DRAW = Common.getDateTimeNow(Common.DATE_TIME_TYPE.type9);
        timeNameFileCapturedAnh = Common.getDateTimeNow(Common.DATE_TIME_TYPE.type12);
        timeSQLSaveAnh = Common.getDateTimeNow(Common.DATE_TIME_TYPE.sqlite1);
        String pathNewAnh = "";

        boolean isTu;
        if (maBdongTuTi == MA_BDONG.B)
            isTu = tutiListViewB.get(pos).isTu;
        else
            isTu = tutiListViewE.get(pos).isTu;


        //clone data old
        switch (typeImage) {
            case IMAGE_CONG_TO:
            case IMAGE_CONG_TO_NIEM_PHONG:
                return null;

            case IMAGE_TUTI:
                pathOldAnh = Common.getRecordDirectoryFolder(isTu ? Common.FOLDER_NAME.FOLDER_ANH_TU.name() : Common.FOLDER_NAME.FOLDER_ANH_TI.name()) + "/" + tutiListViewE.get(pos).anhTuTi.getTEN_ANH();
                break;
            case IMAGE_MACH_NHI_THU_TUTI:
                pathOldAnh = Common.getRecordDirectoryFolder(isTu ? Common.FOLDER_NAME.FOLDER_ANH_TU.name() : Common.FOLDER_NAME.FOLDER_ANH_TI.name()) + "/" + tutiListViewE.get(pos).anhNhiThu.getTEN_ANH();
                break;
            case IMAGE_NIEM_PHONG_TUTI:
                pathOldAnh = Common.getRecordDirectoryFolder(isTu ? Common.FOLDER_NAME.FOLDER_ANH_TU.name() : Common.FOLDER_NAME.FOLDER_ANH_TI.name()) + "/" + tutiListViewE.get(pos).anhNiemPhong.getTEN_ANH();
                break;
        }

        //refresh new Data and get path anh newest
        switch (typeImage) {
            case IMAGE_CONG_TO:
            case IMAGE_CONG_TO_NIEM_PHONG:

                return null;

            case IMAGE_TUTI:
                if (maBdongTuTi == MA_BDONG.E) {
                    tableAnhHientruongOld = tutiListViewE.get(indexTuTi).anhTuTi;
                    tutiListViewE.get(indexTuTi).anhTuTi.setCREATE_DAY(timeSQLSaveAnh);
                    tutiListViewE.get(indexTuTi).anhTuTi.setTEN_ANH(Common.getImageName(typeImage.code, timeNameFileCapturedAnh, MA_DVIQLY, MA_TRAM, onIDataCommom.getID_BBAN_TRTH(), SO_CTO));
                    pathNewAnh = Common.getRecordDirectoryFolder(isTu ? Common.FOLDER_NAME.FOLDER_ANH_TU.name() : Common.FOLDER_NAME.FOLDER_ANH_TI.name()) + "/" + tutiListViewE.get(indexTuTi).anhTuTi.getTEN_ANH();
                } else {
                    tableAnhHientruongOld = tutiListViewB.get(indexTuTi).anhTuTi;
                    tutiListViewB.get(indexTuTi).anhTuTi.setCREATE_DAY(timeSQLSaveAnh);
                    tutiListViewB.get(indexTuTi).anhTuTi.setTEN_ANH(Common.getImageName(typeImage.code, timeNameFileCapturedAnh, MA_DVIQLY, MA_TRAM, onIDataCommom.getID_BBAN_TRTH(), SO_CTO));
                    pathNewAnh = Common.getRecordDirectoryFolder(isTu ? Common.FOLDER_NAME.FOLDER_ANH_TU.name() : Common.FOLDER_NAME.FOLDER_ANH_TI.name()) + "/" + tutiListViewB.get(indexTuTi).anhTuTi.getTEN_ANH();
                }
                break;
            case IMAGE_MACH_NHI_THU_TUTI:
                if (maBdongTuTi == MA_BDONG.E) {
                    tableAnhHientruongOld = tutiListViewE.get(indexTuTi).anhNhiThu;
                    tutiListViewE.get(indexTuTi).anhNhiThu.setCREATE_DAY(timeSQLSaveAnh);
                    tutiListViewE.get(indexTuTi).anhNhiThu.setTEN_ANH(Common.getImageName(typeImage.code, timeNameFileCapturedAnh, MA_DVIQLY, MA_TRAM, onIDataCommom.getID_BBAN_TRTH(), SO_CTO));
                    pathNewAnh = Common.getRecordDirectoryFolder(isTu ? Common.FOLDER_NAME.FOLDER_ANH_TU.name() : Common.FOLDER_NAME.FOLDER_ANH_TI.name()) + "/" + tutiListViewE.get(indexTuTi).anhNhiThu.getTEN_ANH();
                } else {
                    tableAnhHientruongOld = tutiListViewB.get(indexTuTi).anhNhiThu;
                    tutiListViewB.get(indexTuTi).anhNhiThu.setCREATE_DAY(timeSQLSaveAnh);
                    tutiListViewB.get(indexTuTi).anhNhiThu.setTEN_ANH(Common.getImageName(typeImage.code, timeNameFileCapturedAnh, MA_DVIQLY, MA_TRAM, onIDataCommom.getID_BBAN_TRTH(), SO_CTO));
                    pathNewAnh = Common.getRecordDirectoryFolder(isTu ? Common.FOLDER_NAME.FOLDER_ANH_TU.name() : Common.FOLDER_NAME.FOLDER_ANH_TI.name()) + "/" + tutiListViewB.get(indexTuTi).anhNhiThu.getTEN_ANH();
                }

                break;
            case IMAGE_NIEM_PHONG_TUTI:
                if (maBdongTuTi == MA_BDONG.E) {
                    tableAnhHientruongOld = tutiListViewE.get(indexTuTi).anhNiemPhong;
                    tutiListViewE.get(indexTuTi).anhNiemPhong.setCREATE_DAY(timeSQLSaveAnh);
                    tutiListViewE.get(indexTuTi).anhNiemPhong.setTEN_ANH(Common.getImageName(typeImage.code, timeNameFileCapturedAnh, MA_DVIQLY, MA_TRAM, onIDataCommom.getID_BBAN_TRTH(), SO_CTO));
                    pathNewAnh = Common.getRecordDirectoryFolder(isTu ? Common.FOLDER_NAME.FOLDER_ANH_TU.name() : Common.FOLDER_NAME.FOLDER_ANH_TI.name()) + "/" + tutiListViewE.get(indexTuTi).anhNiemPhong.getTEN_ANH();
                } else {
                    tableAnhHientruongOld = tutiListViewB.get(indexTuTi).anhNiemPhong;
                    tutiListViewB.get(indexTuTi).anhNiemPhong.setCREATE_DAY(timeSQLSaveAnh);
                    tutiListViewB.get(indexTuTi).anhNiemPhong.setTEN_ANH(Common.getImageName(typeImage.code, timeNameFileCapturedAnh, MA_DVIQLY, MA_TRAM, onIDataCommom.getID_BBAN_TRTH(), SO_CTO));
                    pathNewAnh = Common.getRecordDirectoryFolder(isTu ? Common.FOLDER_NAME.FOLDER_ANH_TU.name() : Common.FOLDER_NAME.FOLDER_ANH_TI.name()) + "/" + tutiListViewB.get(indexTuTi).anhNiemPhong.getTEN_ANH();
                }

                break;
        }

        Bitmap bitmap = Common.drawTextOnBitmapCongTo(getActivity(), pathOldAnh, TEN_KHANG, TYPE_IMAGE_DRAW, DATE_TIME_DRAW, SO_TUTI_DRAW, SO_CTO_DRAW, MA_DDO_DRAW);

        //rename file ảnh cũ nếu có
        if (!TextUtils.isEmpty(pathOldAnh))
            Common.renameFile(pathOldAnh, pathNewAnh);


        //update rows data và lấy ID refresh data
        //refresh data tableAnh...
        switch (typeImage) {
            case IMAGE_CONG_TO:
            case IMAGE_CONG_TO_NIEM_PHONG:
                return null;

            case IMAGE_TUTI:
                if (maBdongTuTi == MA_BDONG.E) {
                    tutiListViewE.get(indexTuTi).anhTuTi.setMA_NVIEN(onIDataCommom.getMaNVien());
                    tutiListViewE.get(indexTuTi).anhTuTi.setID_BBAN_TUTI(tableBbanTuti.getID_BBAN_TUTI());
                    tutiListViewE.get(indexTuTi).anhTuTi.setID_CHITIET_TUTI(-1);
                    tutiListViewE.get(indexTuTi).anhTuTi.setID_TABLE_ANH_HIENTRUONG((int) mSqlDAO.updateORInsertRows(TABLE_ANH_HIENTRUONG.class, tableAnhHientruongOld, tutiListViewE.get(indexTuTi).anhTuTi));
                } else {
                    tutiListViewB.get(indexTuTi).anhTuTi.setMA_NVIEN(onIDataCommom.getMaNVien());
                    tutiListViewB.get(indexTuTi).anhTuTi.setID_BBAN_TUTI(tableBbanTuti.getID_BBAN_TUTI());
                    tutiListViewB.get(indexTuTi).anhTuTi.setID_CHITIET_TUTI(-1);
                    tutiListViewB.get(indexTuTi).anhTuTi.setID_TABLE_ANH_HIENTRUONG((int) mSqlDAO.updateORInsertRows(TABLE_ANH_HIENTRUONG.class, tableAnhHientruongOld, tutiListViewB.get(indexTuTi).anhTuTi));
                }
                break;
            case IMAGE_MACH_NHI_THU_TUTI:
                if (maBdongTuTi == MA_BDONG.E) {
                    tutiListViewE.get(indexTuTi).anhNhiThu.setMA_NVIEN(onIDataCommom.getMaNVien());
                    tutiListViewE.get(indexTuTi).anhNhiThu.setID_BBAN_TUTI(tableBbanTuti.getID_BBAN_TUTI());
                    tutiListViewE.get(indexTuTi).anhNhiThu.setID_CHITIET_TUTI(-1);
                    tutiListViewE.get(indexTuTi).anhNhiThu.setID_TABLE_ANH_HIENTRUONG((int) mSqlDAO.updateORInsertRows(TABLE_ANH_HIENTRUONG.class, tableAnhHientruongOld, tutiListViewE.get(indexTuTi).anhNhiThu));
                } else {
                    tutiListViewB.get(indexTuTi).anhNhiThu.setMA_NVIEN(onIDataCommom.getMaNVien());
                    tutiListViewB.get(indexTuTi).anhNhiThu.setID_BBAN_TUTI(tableBbanTuti.getID_BBAN_TUTI());
                    tutiListViewB.get(indexTuTi).anhNhiThu.setID_CHITIET_TUTI(-1);
                    tutiListViewE.get(indexTuTi).anhNhiThu.setID_TABLE_ANH_HIENTRUONG((int) mSqlDAO.updateORInsertRows(TABLE_ANH_HIENTRUONG.class, tableAnhHientruongOld, tutiListViewB.get(indexTuTi).anhNhiThu));
                }
                break;
            case IMAGE_NIEM_PHONG_TUTI:
                if (maBdongTuTi == MA_BDONG.E) {
                    tutiListViewE.get(indexTuTi).anhNiemPhong.setMA_NVIEN(onIDataCommom.getMaNVien());
                    tutiListViewE.get(indexTuTi).anhNiemPhong.setID_BBAN_TUTI(tableBbanTuti.getID_BBAN_TUTI());
                    tutiListViewE.get(indexTuTi).anhNiemPhong.setID_CHITIET_TUTI(-1);
                    tutiListViewE.get(indexTuTi).anhNiemPhong.setID_TABLE_ANH_HIENTRUONG((int) mSqlDAO.updateORInsertRows(TABLE_ANH_HIENTRUONG.class, tableAnhHientruongOld, tutiListViewE.get(indexTuTi).anhNiemPhong));
                } else {
                    tutiListViewB.get(indexTuTi).anhNiemPhong.setMA_NVIEN(onIDataCommom.getMaNVien());
                    tutiListViewB.get(indexTuTi).anhNiemPhong.setID_BBAN_TUTI(tableBbanTuti.getID_BBAN_TUTI());
                    tutiListViewB.get(indexTuTi).anhNiemPhong.setID_CHITIET_TUTI(-1);
                    tutiListViewB.get(indexTuTi).anhNiemPhong.setID_TABLE_ANH_HIENTRUONG((int) mSqlDAO.updateORInsertRows(TABLE_ANH_HIENTRUONG.class, tableAnhHientruongOld, tutiListViewB.get(indexTuTi).anhNiemPhong));
                }
                break;
        }

        return bitmap;
    }

    private boolean isFullRequireDataTuti() throws Exception {
        //check bitmap
        Bitmap bitmap = (ivAnhTu.getDrawable() == null) ? null : ((BitmapDrawable) ivAnhTu.getDrawable()).getBitmap();
        if (bitmap == null)
            throw new Exception("Vui lòng chụp ảnh TU treo!");

        bitmap = (ivAnhTi.getDrawable() == null) ? null : ((BitmapDrawable) ivAnhTi.getDrawable()).getBitmap();
        if (bitmap == null)
            throw new Exception("Vui lòng chụp ảnh TI treo!");

        bitmap = (ivAnhNhiThuTu.getDrawable() == null) ? null : ((BitmapDrawable) ivAnhNhiThuTu.getDrawable()).getBitmap();
        if (bitmap == null)
            throw new Exception("Vui lòng chụp ảnh nhị thứ của TU treo!");

        bitmap = (ivAnhNhiThuTi.getDrawable() == null) ? null : ((BitmapDrawable) ivAnhNhiThuTi.getDrawable()).getBitmap();
        if (bitmap == null)
            throw new Exception("Vui lòng chụp ảnh nhị thứ của TI treo!");

        bitmap = (ivAnhNiemPhongTu.getDrawable() == null) ? null : ((BitmapDrawable) ivAnhNiemPhongTu.getDrawable()).getBitmap();
        if (bitmap == null)
            throw new Exception("Vui lòng chụp ảnh niêm phong của TU treo!");

        bitmap = (ivAnhNiemPhongTi.getDrawable() == null) ? null : ((BitmapDrawable) ivAnhNiemPhongTi.getDrawable()).getBitmap();
        if (bitmap == null)
            throw new Exception("Vui lòng chụp ảnh niêm phong của TI treo!");

        return true;
    }

    private void clickFab() {
        fabTtKh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollViewTuti.post(new Runnable() {
                    @Override
                    public void run() {
                        tvfabTtKh.getParent().requestChildFocus(tvfabTtKh, tvfabTtKh);
                        scrollViewTuti.scrollTo(0, tvfabTtKh.getTop());
                    }
                });
            }
        });


        fabTtTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollViewTuti.post(new Runnable() {
                    @Override
                    public void run() {
                        tvfabTtTu.getParent().requestChildFocus(tvfabTtTu, tvfabTtTu);
                        scrollViewTuti.scrollTo(0, tvfabTtTu.getTop());
                    }
                });
            }
        });

        fabTtTi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollViewTuti.post(new Runnable() {
                    @Override
                    public void run() {
                        tvfabTtTi.getParent().requestChildFocus(tvfabTtTi, tvfabTtTi);
                        scrollViewTuti.scrollTo(0, tvfabTtTi.getTop());
                    }
                });
            }
        });

        fabTtCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollViewTuti.post(new Runnable() {
                    @Override
                    public void run() {
                        tvfabTtCamera.getParent().requestChildFocus(tvfabTtCamera, tvfabTtCamera);
                        scrollViewTuti.scrollTo(0, tvfabTtCamera.getTop());
                    }
                });
            }
        });

        fabTtCannhapsaulap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollViewTuti.post(new Runnable() {
                    @Override
                    public void run() {
                        tvfabTtCannhapsaulap.getParent().requestChildFocus(tvfabTtCannhapsaulap, tvfabTtCannhapsaulap);
                        scrollViewTuti.scrollTo(0, tvfabTtCannhapsaulap.getTop());
                    }
                });
            }
        });
    }

    public int getPos() {
        return pos;
    }

    //endregion
    public interface IOnTthtHnBBanTutiFragment {
    }

    private static class DataViewIncludeTuTi extends LinearLayout {
        View rowView;
        ListenerViewInCludeTuTi listener;

        EditText etSoTuTi, etSoBBKdinh, etNuocSX, etDienApTuTi, etTysobienTuTi, etTysoVongTuTi, etNgayKDinh, etTinhTrangVanHanh;
        ImageView ivAnhTuti, ivAnhNhiThu, ivAnhNiemPhong;
        Button btnAnhTuti, btnAnhNhiThu, btnAnhNiemPhong, btnSaveDataTuTi;

        TextView tvTitle;
        RadioGroup rgTuTi;
        RadioButton rbTu, rbTi;


        int index;
        boolean isTu;
        MA_BDONG MA_BDONG;
        boolean flagChangeData;

        String stringEtSoTuTi = "", stringEtSoBBKdinh = "",
                stringEtNuocSX = "", stringEtDienApTuTi = "",
                stringEtTysobienTuTi = "", stringEtTysoVongTuTi = "",
                stringEtNgayKDinh = "", stringEtTinhTrangVanHanh = "";

        Bitmap bitmapAnhTuTi, bitmapAnhNhiThu, bitmapAnhNiemPhong;


        TABLE_ANH_HIENTRUONG anhTuTi;
        TABLE_ANH_HIENTRUONG anhNhiThu;
        TABLE_ANH_HIENTRUONG anhNiemPhong;

        TABLE_CHITIET_TUTI tuti;

        public DataViewIncludeTuTi(Context context, ListenerViewInCludeTuTi listener) {
            super(context);

            this.listener = listener;

            rowView = LayoutInflater.from(context).inflate(R.layout.ttht_include_tuti, null);
            etSoTuTi = (EditText) rowView.findViewById(R.id.et_sotuti_thao_add);
            etSoBBKdinh = (EditText) rowView.findViewById(R.id.et_sobbkdinh_thao_add);
            etNuocSX = (EditText) rowView.findViewById(R.id.et_nuocsx_tuti_thao_add);
            etDienApTuTi = (EditText) rowView.findViewById(R.id.et_dienaptuti_thao_add);

            etTysobienTuTi = (EditText) rowView.findViewById(R.id.et_tysobientuti_thao_add);
            etTysoVongTuTi = (EditText) rowView.findViewById(R.id.et_tysovongtuti_thao_add);

            etNgayKDinh = (EditText) rowView.findViewById(R.id.et_ngaykdinh_tuti_thao_add);
            etTinhTrangVanHanh = (EditText) rowView.findViewById(R.id.et_ttvanhanh_tuti_thao_add);


            ivAnhTuti = (ImageView) rowView.findViewById(R.id.iv_anh_tuti_thao);
            ivAnhNhiThu = (ImageView) rowView.findViewById(R.id.iv_anhnhithu_tuti_thao);
            ivAnhNiemPhong = (ImageView) rowView.findViewById(R.id.iv_anhniemphong_tuti_thao);

            btnAnhTuti = (Button) rowView.findViewById(R.id.btn_anh_tuti_thao);
            btnAnhNhiThu = (Button) rowView.findViewById(R.id.btn_saveannhithu_tuti_thao);
            btnAnhNiemPhong = (Button) rowView.findViewById(R.id.btn_save_anhniemphong_tuti_thao);

            tvTitle = (TextView) rowView.findViewById(R.id.tv_tittle_tuti);
            rgTuTi = (RadioGroup) rowView.findViewById(R.id.rg_tuti);
            rbTu = (RadioButton) rowView.findViewById(R.id.rb_tu);
            rbTi = (RadioButton) rowView.findViewById(R.id.rb_ti);

            btnSaveDataTuTi = rowView.findViewById(R.id.btn_save_data_tuti);

            fillData();

            clickButton();

            this.addView(rowView);
        }

        private void clickButton() {
            //region ảnh
            btnAnhTuti.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.clickbtnAnhTuti(isTu, index);

//                    if(bitmap == null)
//                        return;
//
//                    bitmapAnhTuTi = bitmap;
//                    ivAnhTuti.setImageBitmap(bitmapAnhTuTi);
                }
            });

            btnAnhNhiThu.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.clickbtnAnhNhiThu(isTu, index);
//                    if(bitmap == null)
//                        return;
//
//                    bitmapAnhNhiThu = bitmap;
//                    ivAnhNhiThu.setImageBitmap(bitmapAnhNhiThu);
                }
            });

            btnAnhNiemPhong.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.clickbtnAnhNiemPhong(isTu, index);
//                    Bitmap bitmap =  listener.clickbtnAnhTuti();
//
//                    if(bitmap == null)
//                        return;
//
//                    bitmapAnhNiemPhong = bitmap;
//                    ivAnhNiemPhong.setImageBitmap(bitmapAnhNiemPhong);
                }
            });


            ivAnhTuti.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        //get bitmap
                        if (bitmapAnhTuTi == null) {
                            return;
                        }


                        //zoom
                        Common.zoomImage(getContext(), bitmapAnhTuTi);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ((TthtHnBaseActivity) getContext()).showSnackBar(Common.MESSAGE.ex081.getContent(), e.getMessage(), null);
                    }
                }
            });

            ivAnhNhiThu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        //get bitmap
                        if (bitmapAnhNhiThu == null) {
                            return;
                        }


                        //zoom
                        Common.zoomImage(getContext(), bitmapAnhNhiThu);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ((TthtHnBaseActivity) getContext()).showSnackBar(Common.MESSAGE.ex081.getContent(), e.getMessage(), null);
                    }
                }
            });

            ivAnhNiemPhong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        //get bitmap
                        if (bitmapAnhNiemPhong == null) {
                            return;
                        }


                        //zoom
                        Common.zoomImage(getContext(), bitmapAnhNiemPhong);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ((TthtHnBaseActivity) getContext()).showSnackBar(Common.MESSAGE.ex081.getContent(), e.getMessage(), null);
                    }
                }
            });
            //endregion

            //region TU hoặc Ti
            rbTi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (rbTi.isPressed()) {
                        if (b) {
                            isTu = false;
                            tvTitle.setText("Thông tin TI " + ((MA_BDONG == MA_BDONG.E) ? "Tháo " : "Treo ") + index + 1);

                            rowView.post(new Runnable() {
                                @Override
                                public void run() {
                                    Common.runAnimationClickView(tvTitle, R.anim.tththn_scale_view_pull, 100);
                                }
                            });

                            refreshView();
                        }
                    }
                }
            });

            rbTu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (rbTu.isPressed()) {
                        if (b) {
                            isTu = true;
                            tvTitle.setText("Thông tin TU " + ((MA_BDONG == MA_BDONG.E) ? "Tháo " : "Treo ") + index + 1);

                            rowView.post(new Runnable() {
                                @Override
                                public void run() {
                                    Common.runAnimationClickView(tvTitle, R.anim.tththn_scale_view_pull, 100);
                                }
                            });

                            refreshView();
                        }
                    }
                }
            });

            btnSaveDataTuTi.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.saveDataTuTi(index);
                }
            });
            //endregion
        }

        private void refreshView() {
            rowView.post(new Runnable() {
                @Override
                public void run() {
                    rowView.invalidate();
                }
            });
        }

        private void refreshView(final View view) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.invalidate();
                }
            });
        }

        private void fillData() {
            tvTitle.setText("Thông tin " + ((isTu) ? "TU " : "TI ") + ((MA_BDONG == MA_BDONG.E) ? "Tháo " : "Treo ") + index + 1);
            rbTu.setChecked(isTu);
            rbTi.setChecked(!isTu);


            etSoTuTi.setText(stringEtSoTuTi);
            etSoBBKdinh.setText(stringEtSoBBKdinh);
            etNuocSX.setText(stringEtNuocSX);
            etDienApTuTi.setText(stringEtDienApTuTi);
            etTysobienTuTi.setText(stringEtTysobienTuTi);
            etTysoVongTuTi.setText(stringEtTysoVongTuTi);
            etNgayKDinh.setText(stringEtNgayKDinh);
            etTinhTrangVanHanh.setText(stringEtTinhTrangVanHanh);

            etSoTuTi.setHint(stringEtSoTuTi);
            etSoBBKdinh.setHint(stringEtSoBBKdinh);
            etNuocSX.setHint(stringEtNuocSX);
            etDienApTuTi.setHint(stringEtDienApTuTi);
            etTysobienTuTi.setHint(stringEtTysobienTuTi);
            etTysoVongTuTi.setHint(stringEtTysoVongTuTi);
            etNgayKDinh.setHint(stringEtNgayKDinh);
            etTinhTrangVanHanh.setHint(stringEtTinhTrangVanHanh);


        }

        public void setBitmap(Common.TYPE_IMAGE typeImage, Bitmap bitmap) {
            switch (typeImage) {
                case IMAGE_CONG_TO:
                    break;
                case IMAGE_CONG_TO_NIEM_PHONG:
                    break;
                case IMAGE_TUTI:
                    ivAnhTuti.setImageBitmap(bitmap);
                    break;
                case IMAGE_MACH_NHI_THU_TUTI:
                    ivAnhNhiThu.setImageBitmap(bitmap);
                    break;
                case IMAGE_NIEM_PHONG_TUTI:
                    ivAnhNiemPhong.setImageBitmap(bitmap);
                    break;
            }
        }

        public String checkCanSave() {
            //check bitmap
            String messageSave = "";
            messageSave = (bitmapAnhTuTi == null) ? messageSave : "Vui lòng chụp ảnh niêm phong!";

            messageSave = (bitmapAnhTuTi == null) ? messageSave : "Vui lòng chụp ảnh nhị thứ!";

            messageSave = (bitmapAnhTuTi == null) ? messageSave : "Vui lòng chụp ảnh niêm phong!";

            boolean isHasChangeDataEditText = etSoTuTi.getText().toString().equalsIgnoreCase(etSoTuTi.getHint().toString())
                    || etSoBBKdinh.getText().toString().equalsIgnoreCase(etSoBBKdinh.getHint().toString())
                    || etNuocSX.getText().toString().equalsIgnoreCase(etNuocSX.getHint().toString())
                    || etDienApTuTi.getText().toString().equalsIgnoreCase(etDienApTuTi.getHint().toString())
                    || etTysobienTuTi.getText().toString().equalsIgnoreCase(etTysobienTuTi.getHint().toString())
                    || etTysoVongTuTi.getText().toString().equalsIgnoreCase(etTysoVongTuTi.getHint().toString())
                    || etNgayKDinh.getText().toString().equalsIgnoreCase(etNgayKDinh.getHint().toString())
                    || etTinhTrangVanHanh.getText().toString().equalsIgnoreCase(etTinhTrangVanHanh.getHint().toString());


            messageSave = isHasChangeDataEditText ? "Cần lưu thông tin Tu hoặc Ti trước" : messageSave;

            return messageSave;
        }

        public void setHintAfterSave() {
            stringEtSoTuTi = etSoTuTi.getText().toString();
            stringEtSoBBKdinh = etSoBBKdinh.getText().toString();
            stringEtNuocSX = etNuocSX.getText().toString();
            stringEtDienApTuTi = etDienApTuTi.getText().toString();
            stringEtTysobienTuTi = etTysobienTuTi.getText().toString();
            stringEtTysoVongTuTi = etTysoVongTuTi.getText().toString();
            stringEtNgayKDinh = etNgayKDinh.getText().toString();
            stringEtTinhTrangVanHanh = etTinhTrangVanHanh.getText().toString();

            etSoTuTi.setHint(stringEtSoTuTi);
            etSoBBKdinh.setHint(stringEtSoBBKdinh);
            etNuocSX.setHint(stringEtNuocSX);
            etDienApTuTi.setHint(stringEtDienApTuTi);
            etTysobienTuTi.setHint(stringEtTysobienTuTi);
            etTysoVongTuTi.setHint(stringEtTysoVongTuTi);
            etNgayKDinh.setHint(stringEtNgayKDinh);
            etTinhTrangVanHanh.setHint(stringEtTinhTrangVanHanh);
        }

        interface ListenerViewInCludeTuTi {
            //setOnCheckedChangeListener

            void saveDataTuTi(int pos);


            void clickbtnAnhTuti(boolean isTu, int pos);

            void clickbtnAnhNhiThu(boolean isTu, int pos);

            void clickbtnAnhNiemPhong(boolean isTu, int pos);
        }
    }

}
