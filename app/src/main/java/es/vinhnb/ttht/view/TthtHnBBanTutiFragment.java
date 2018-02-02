package es.vinhnb.ttht.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
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
import esolutions.com.esdatabaselib.baseSqlite.SqlHelper;

import static android.app.Activity.RESULT_OK;
import static es.vinhnb.ttht.common.Common.TYPE_IMAGE.IMAGE_MACH_NHI_THU_TUTI;
import static es.vinhnb.ttht.common.Common.TYPE_IMAGE.IMAGE_NIEM_PHONG_TUTI;
import static es.vinhnb.ttht.common.Common.TYPE_IMAGE.IMAGE_TUTI;
import static es.vinhnb.ttht.view.TthtHnBaseActivity.BUNDLE_IS_NGOAIHIENTRUONG_BBAN_TUTI;
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


    //row E
    @BindView(R.id.include_tutiE)
    LinearLayout rlIncludeTuTiE;

    @BindView(R.id.bt_add_tuti_E)
    Button btnAddTuTiE;

    @BindView(R.id.v_mark_tutiE)
    View vMarkTuTiE;

    //row B
    @BindView(R.id.include_tutiB)
    LinearLayout rlIncludeTuTiB;

    @BindView(R.id.bt_add_tuti_B)
    Button btnAddTuTiB;

    @BindView(R.id.v_mark_tutiB)
    View vMarkTuTiB;

    int indexTuTi = -1;
    MA_BDONG maBdongTuTi;


    @BindView(R.id.title_bbantuti)
    TextView tvTitleBbanTuTi;

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
    private String isBBTuTiNgoaiHienTruong;
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
    private Common.IS_BBAN_HIENTRUONG bbanTuTiLapNgoaiHienTruong;


    public TthtHnBBanTutiFragment() {
        // Required empty public constructor
    }

    public static TthtHnBBanTutiFragment newInstance(int pos, String is_bban_hientruong) {

        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_POS, pos);
        bundle.putString(BUNDLE_IS_NGOAIHIENTRUONG_BBAN_TUTI, is_bban_hientruong);

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
            isBBTuTiNgoaiHienTruong = getArguments().getString(BUNDLE_IS_NGOAIHIENTRUONG_BBAN_TUTI, "");
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
            if (requestCode == CAMERA_REQUEST_ANH_TUTI && resultCode == RESULT_OK) {
                onActivityResultCapture(IMAGE_TUTI);
            }

            if (requestCode == CAMERA_REQUEST_ANH_NHITHU_TUTI && resultCode == RESULT_OK) {
                onActivityResultCapture(IMAGE_MACH_NHI_THU_TUTI);
            }

            if (requestCode == CAMERA_REQUEST_ANH_NIEMPHONG_TUTI && resultCode == RESULT_OK) {
                onActivityResultCapture(IMAGE_NIEM_PHONG_TUTI);
            }
        } catch (Exception e) {
            ((TthtHnBaseActivity) getActivity()).showSnackBar(Common.MESSAGE.ex08.getContent(), e.getMessage(), null);
        }
    }

    private void onActivityResultCapture(Common.TYPE_IMAGE typeImage) throws Exception {
        String MA_DVIQLY = tableBbanCto.getMA_DVIQLY();
        String MA_TRAM = tableBbanCto.getMA_TRAM();
        String SO_CTO = (maBdongTuTi == MA_BDONG.B) ? tableChitietCtoB.getSO_CTO() : tableChitietCtoE.getSO_CTO();
        String TEN_ANH = "";
        String pathURICapturedAnh = null;

        boolean isTu = false;
        if (maBdongTuTi == MA_BDONG.E) {
            isTu = Common.IS_TU.findIS_TU(tutiListViewE.get(pos).tuti.getIS_TU()).code;
        } else
            isTu = Common.IS_TU.findIS_TU(tutiListViewB.get(pos).tuti.getIS_TU()).code;

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
        if (TextUtils.isEmpty(isBBTuTiNgoaiHienTruong)) {
            bbanTuTiLapNgoaiHienTruong = Common.IS_BBAN_HIENTRUONG.LAP_NGOAI_HIENTRUONG;
        } else {
            bbanTuTiLapNgoaiHienTruong = Common.IS_BBAN_HIENTRUONG.findIS_BBAN_HIENTRUONG(isBBTuTiNgoaiHienTruong);
        }

        //get Data Chi tiet cong to
        String[] agrs = new String[]{String.valueOf(onIDataCommom.getID_BBAN_TRTH()), MA_BDONG.B.code, onIDataCommom.getMaNVien()};
        List<TABLE_CHITIET_CTO> tableChitietCtoList = mSqlDAO.getChiTietCongto(agrs);
        if (tableChitietCtoList.size() != 0)
            tableChitietCtoB = tableChitietCtoList.get(0);

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
        String MA_CLOAI = (maBdongTuTi == MA_BDONG.B) ? tableChitietCtoB.getMA_CLOAI() : tableChitietCtoE.getMA_CLOAI();
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
        List<TABLE_BBAN_TUTI> tableBbanTutiList = mSqlDAO.getBBanTuti(tableBbanCto.getID_BBAN_CONGTO(), onIDataCommom.getMaNVien());
        if (tableBbanTutiList.size() != 0)
            tableBbanTuti = tableBbanTutiList.get(0);


        //get Data chi tiet tuti
        tutiListB = new ArrayList<>();
        tutiListE = new ArrayList<>();
        List<TABLE_CHITIET_TUTI> tableChitietTutiListB = mSqlDAO.getChitietTuTi(tableChitietCtoB.getID_CHITIET_CTO(), onIDataCommom.getMaNVien());
        for (int i = 0; i < tableChitietTutiListB.size(); i++) {
            TABLE_CHITIET_TUTI tableChitietTuti = tableChitietTutiListB.get(i);

            if (tableChitietTuti.getMA_BDONG().equals(MA_BDONG.B.code)) {
                tutiListB.add(tableChitietTuti);
            } else
                tutiListE.add(tableChitietTuti);
        }


        List<TABLE_CHITIET_TUTI> tableChitietTutiListE = mSqlDAO.getChitietTuTi(tableChitietCtoE.getID_CHITIET_CTO(), onIDataCommom.getMaNVien());
        for (int i = 0; i < tableChitietTutiListE.size(); i++) {
            TABLE_CHITIET_TUTI tableChitietTuti = tableChitietTutiListE.get(i);

            if (tableChitietTuti.getMA_BDONG().equals(MA_BDONG.B.code)) {
                tutiListB.add(tableChitietTuti);
            } else
                tutiListE.add(tableChitietTuti);
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


        //tu ti thao
        for (int i = 0; i < tutiListE.size(); i++) {
            TABLE_ANH_HIENTRUONG anhTuTi = null, anhNhiThu = null, anhNiemPhong = null;
            String[] argsAnh = new String[]{onIDataCommom.getMaNVien(), String.valueOf(tutiListE.get(0).getID_CHITIET_CTO())};
            List<TABLE_ANH_HIENTRUONG> tableAnhHientruongList = mSqlDAO.getAnhHienTruong(argsAnh, IMAGE_TUTI);
            if (tableAnhHientruongList.size() != 0)
                anhTuTi = tableAnhHientruongList.get(0);

            tableAnhHientruongList = mSqlDAO.getAnhHienTruong(argsAnh, IMAGE_MACH_NHI_THU_TUTI);
            if (tableAnhHientruongList.size() != 0)
                anhNhiThu = tableAnhHientruongList.get(0);

            tableAnhHientruongList = mSqlDAO.getAnhHienTruong(argsAnh, IMAGE_NIEM_PHONG_TUTI);
            if (tableAnhHientruongList.size() != 0)
                anhNiemPhong = tableAnhHientruongList.get(0);

            Common.IS_BBAN_HIENTRUONG tutiLapNgoaiHienTruong = Common.IS_BBAN_HIENTRUONG.findIS_BBAN_HIENTRUONG(tutiListB.get(i).getIS_BBAN_HIENTRUONG());

            DataViewIncludeTuTi dataViewIncludeTuTi = new DataViewIncludeTuTi(getActivity(), listenerTuTiE, i, MA_BDONG.E, tutiListE.get(i), anhTuTi, anhNhiThu, anhNiemPhong,
//                    bbanTuTiLapNgoaiHienTruong,
                    tutiLapNgoaiHienTruong);
            tutiListViewE.add(dataViewIncludeTuTi);
            rlIncludeTuTiE.addView(dataViewIncludeTuTi);

            indexTuTi = i;
            maBdongTuTi = MA_BDONG.E;
        }

        //tu ti treo
        for (int i = 0; i < tutiListB.size(); i++) {

            TABLE_ANH_HIENTRUONG anhTuTi = null, anhNhiThu = null, anhNiemPhong = null;
            String[] argsAnh = new String[]{onIDataCommom.getMaNVien(), String.valueOf(tutiListB.get(0).getID_CHITIET_CTO())};
            List<TABLE_ANH_HIENTRUONG> tableAnhHientruongList = mSqlDAO.getAnhHienTruong(argsAnh, IMAGE_TUTI);
            if (tableAnhHientruongList.size() != 0)
                anhTuTi = tableAnhHientruongList.get(0);

            tableAnhHientruongList = mSqlDAO.getAnhHienTruong(argsAnh, IMAGE_MACH_NHI_THU_TUTI);
            if (tableAnhHientruongList.size() != 0)
                anhNhiThu = tableAnhHientruongList.get(0);


            tableAnhHientruongList = mSqlDAO.getAnhHienTruong(argsAnh, IMAGE_NIEM_PHONG_TUTI);
            if (tableAnhHientruongList.size() != 0)
                anhNiemPhong = tableAnhHientruongList.get(0);

            Common.IS_BBAN_HIENTRUONG tutiLapNgoaiHienTruong = Common.IS_BBAN_HIENTRUONG.findIS_BBAN_HIENTRUONG(tutiListB.get(i).getIS_BBAN_HIENTRUONG());
            DataViewIncludeTuTi dataViewIncludeTuTi = new DataViewIncludeTuTi(getActivity(), listenerTuTiB, i, MA_BDONG.B, tutiListB.get(i), anhTuTi, anhNhiThu, anhNiemPhong, tutiLapNgoaiHienTruong);

            tutiListViewB.add(dataViewIncludeTuTi);
            rlIncludeTuTiB.addView(dataViewIncludeTuTi);

            indexTuTi = i;
            maBdongTuTi = MA_BDONG.B;
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
//        if (tableAnhHientruongList.size(  ) != 0)
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


//    private void fillInfoSauLap(Common.TRANG_THAI_DU_LIEU trangThaiDuLieu) {
//        if (tableChitietCto != null) {
//            etDongdienSaulap.setText(tableChitietCto.getDONG_DIEN_SAULAP_TUTI());
//            etDienapSaulap.setText(tableChitietCto.getDIEN_AP_SAULAP_TUTI());
//            etHesoKSaulap.setText(tableChitietCto.getHANGSO_K_SAULAP_TUTI());
//            etCCXSaulap.setText(String.valueOf(tableChitietCto.getCAP_CX_SAULAP_TUTI()));
//
//
//            etLapquaTuSaulap.setText(tableChitietCto.getSO_TU_SAULAP_TUTI());
//            etLapquaTiSaulap.setText(tableChitietCto.getSO_TI_SAULAP_TUTI());
//            etHesonhanSaulap.setText(String.valueOf(tableChitietCto.getHS_NHAN_SAULAP_TUTI()));
//
//
//            //chi so
//            Common.LOAI_CTO loaiCto = Common.LOAI_CTO.findLOAI_CTO(tableChitietCto.getLOAI_CTO());
//            String CHI_SO = tableChitietCto.getCHI_SO_SAULAP_TUTI();
//            HashMap<String, String> dataCHI_SO = Common.spilitCHI_SO(loaiCto, CHI_SO);
//
//            etBTSaulap.setText(TextUtils.isEmpty(dataCHI_SO.get(Common.BO_CHISO.BT.code)) ? "0" : dataCHI_SO.get(Common.BO_CHISO.BT.code));
//            etCDSaulap.setText(TextUtils.isEmpty(dataCHI_SO.get(Common.BO_CHISO.CD.code)) ? "0" : dataCHI_SO.get(Common.BO_CHISO.CD.code));
//            etTDSaulap.setText(TextUtils.isEmpty(dataCHI_SO.get(Common.BO_CHISO.TD.code)) ? "0" : dataCHI_SO.get(Common.BO_CHISO.TD.code));
//            etTongPSaulap.setText(TextUtils.isEmpty(dataCHI_SO.get(Common.BO_CHISO.SG.code)) ? "0" : dataCHI_SO.get(Common.BO_CHISO.SG.code));
//            etTongQSaulap.setText(TextUtils.isEmpty(dataCHI_SO.get(Common.BO_CHISO.VC.code)) ? "0" : dataCHI_SO.get(Common.BO_CHISO.VC.code));
//
//            //set enable
//
//            etDongdienSaulap.setEnabled(true);
//            etDienapSaulap.setEnabled(true);
//            etHesoKSaulap.setEnabled(true);
//            etCCXSaulap.setEnabled(true);
//            etLapquaTuSaulap.setEnabled(true);
//            etLapquaTiSaulap.setEnabled(true);
//            etHesonhanSaulap.setEnabled(true);
//
//            etBTSaulap.setEnabled(true);
//            etCDSaulap.setEnabled(true);
//            etTDSaulap.setEnabled(true);
//            etTongPSaulap.setEnabled(true);
//            etTongQSaulap.setEnabled(true);
//
//            if (trangThaiDuLieu == Common.TRANG_THAI_DU_LIEU.DANG_CHO_XAC_NHAN_CMIS) {
//                etDongdienSaulap.setEnabled(false);
//                etDienapSaulap.setEnabled(false);
//                etHesoKSaulap.setEnabled(false);
//                etCCXSaulap.setEnabled(false);
//                etLapquaTuSaulap.setEnabled(false);
//                etLapquaTiSaulap.setEnabled(false);
//                etHesonhanSaulap.setEnabled(false);
//
//                etBTSaulap.setEnabled(false);
//                etCDSaulap.setEnabled(false);
//                etTDSaulap.setEnabled(false);
//                etTongPSaulap.setEnabled(false);
//                etTongQSaulap.setEnabled(false);
//            }
//
//
//            //chiso
//
//        }
//    }


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

//        if (tableChitietCto != null) {
//            tvSoNoTuti.setText(tableChitietCto.getSO_CTO());
//
//            tvSoNoTuti.setHint(tableChitietCto.getSO_CTO());
//        }
    }

    @Override
    public void setAction(Bundle savedInstanceState) throws Exception {
        //click fab
        clickButton();

        clickFab();

        clickMenuBottom();


    }

    DataViewIncludeTuTi.ListenerViewInCludeTuTi listenerTuTiE = new DataViewIncludeTuTi.ListenerViewInCludeTuTi() {

        @Override
        public void saveDataTuTi(int pos) {
            try {
                indexTuTi = pos;
                maBdongTuTi = MA_BDONG.E;

                String messageSave = tutiListViewE.get(indexTuTi).checkCanSave();
                if (!TextUtils.isEmpty(messageSave))
                    throw new Exception(messageSave);

                saveDataTuti(pos);

                //reset index nếu thành công
                indexTuTi = -1;
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


    DataViewIncludeTuTi.ListenerViewInCludeTuTi listenerTuTiB = new DataViewIncludeTuTi.ListenerViewInCludeTuTi() {

        @Override
        public void saveDataTuTi(int pos) {
            try {
                indexTuTi = pos;
                maBdongTuTi = MA_BDONG.B;

                String messageSave = tutiListViewB.get(indexTuTi).checkCanSave();
                if (!TextUtils.isEmpty(messageSave))
                    throw new Exception(messageSave);

                saveDataTuti(pos);

                //reset index nếu thành công
                indexTuTi = -1;
            } catch (Exception e) {
                e.printStackTrace();
                ((TthtHnBaseActivity) getActivity()).showSnackBar(Common.MESSAGE.ex08.getContent(), e.getMessage(), null);
            }
        }

        @Override
        public void clickbtnAnhTuti(boolean isTu, int pos) {
            try {
                indexTuTi = pos;
                maBdongTuTi = MA_BDONG.B;
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
                maBdongTuTi = MA_BDONG.B;
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
                maBdongTuTi = MA_BDONG.B;
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
                        if (maBdongTuTi == MA_BDONG.B) {
                            String message = tutiListViewB.get(indexTuTi).checkCanAddNew();
                            if (TextUtils.isEmpty(message)) {
                                scrollViewTuti.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            DataViewIncludeTuTi dataViewIncludeTuTi = new DataViewIncludeTuTi(getActivity(), listenerTuTiE, tutiListE.size(), MA_BDONG.E, null, null, null, null, Common.IS_BBAN_HIENTRUONG.LAP_NGOAI_HIENTRUONG);
                                            tutiListViewE.add(dataViewIncludeTuTi);
                                            rlIncludeTuTiE.addView(dataViewIncludeTuTi);

                                            viewRoot.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    indexTuTi = tutiListE.size();
                                                    maBdongTuTi = MA_BDONG.E;
                                                    Common.runAnimationClickView(rlIncludeTuTiE, R.anim.tththn_twinking_view, 50);
                                                    viewRoot.invalidate();

                                                    scrollViewTuti.post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Rect rectf = new Rect();
                                                            tutiListViewE.get(indexTuTi).rlMark.getLocalVisibleRect(rectf);
                                                            tutiListViewE.get(indexTuTi).rlMark.getParent().requestChildFocus(tutiListViewE.get(indexTuTi).rlMark, tutiListViewE.get(indexTuTi).rlMark);
                                                            scrollViewTuti.scrollTo(0, rectf.top);
                                                            scrollViewTuti.smoothScrollTo(0, rectf.top);
                                                        }
                                                    });

                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            ((TthtHnBaseActivity) getActivity()).showSnackBar(Common.MESSAGE.ex08.getContent(), e.getMessage(), null);
                                        }
                                    }
                                });
                            } else {

                                scrollViewTuti.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Rect rectf = new Rect();
                                        tutiListViewB.get(indexTuTi).rlMark.getLocalVisibleRect(rectf);
                                        tutiListViewB.get(indexTuTi).rlMark.getParent().requestChildFocus(tutiListViewB.get(indexTuTi).rlMark, tutiListViewB.get(indexTuTi).rlMark);
                                        scrollViewTuti.scrollTo(0, rectf.top);
                                        scrollViewTuti.smoothScrollTo(0, rectf.top);
                                    }
                                });

                                ((TthtHnBaseActivity) getActivity()).showSnackBar("Cần lưu thông tin Tu Ti Treo thứ " + (indexTuTi + 1) + " trước khi thêm mới Tu Ti Treo: \n" + message, null, null);
                            }
                            return;
                        } else {
                            String message = tutiListViewE.get(indexTuTi).checkCanAddNew();
                            if (TextUtils.isEmpty(message)) {
                                viewRoot.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        DataViewIncludeTuTi dataViewIncludeTuTi = new DataViewIncludeTuTi(getActivity(), listenerTuTiE, tutiListE.size(), MA_BDONG.E, null, null, null, null, Common.IS_BBAN_HIENTRUONG.LAP_NGOAI_HIENTRUONG);
                                        tutiListViewE.add(dataViewIncludeTuTi);
                                        rlIncludeTuTiE.addView(dataViewIncludeTuTi);
                                        viewRoot.invalidate();
                                        Common.runAnimationClickView(rlIncludeTuTiE, R.anim.tththn_twinking_view, 50);
                                        indexTuTi = tutiListE.size();
                                        maBdongTuTi = MA_BDONG.E;

                                        scrollViewTuti.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Rect rectf = new Rect();
                                                tutiListViewE.get(indexTuTi).rlMark.getLocalVisibleRect(rectf);
                                                tutiListViewE.get(indexTuTi).rlMark.getParent().requestChildFocus(tutiListViewE.get(indexTuTi).rlMark, tutiListViewE.get(indexTuTi).rlMark);
                                                scrollViewTuti.scrollTo(0, rectf.top);
                                                scrollViewTuti.smoothScrollTo(0, rectf.top);
                                            }
                                        });
                                    }
                                });


                            } else {
                                scrollViewTuti.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Rect rectf = new Rect();
                                        tutiListViewE.get(indexTuTi).rlMark.getLocalVisibleRect(rectf);
                                        tutiListViewE.get(indexTuTi).rlMark.getParent().requestChildFocus(tutiListViewE.get(indexTuTi).rlMark, tutiListViewE.get(indexTuTi).rlMark);
                                        scrollViewTuti.scrollTo(0, rectf.top);
                                        scrollViewTuti.smoothScrollTo(0, rectf.top);
                                    }
                                });

                                ((TthtHnBaseActivity) getActivity()).showSnackBar("Cần lưu thông tin Tu Ti Tháo thứ " + (indexTuTi + 1) + " trước khi thêm mới Tu Ti Tháo: \n" + message, null, null);
                            }
                            return;
                        }
                    } else {
                        int countTU = 0;
                        int countTI = 0;
                        for (DataViewIncludeTuTi viewIncludeTuTi : tutiListViewE) {
                            if (Common.IS_TU.findIS_TU(viewIncludeTuTi.tuti.getIS_TU()) == Common.IS_TU.TU) {
                                countTU++;
                            } else {
                                countTI++;
                            }
                        }

                        if (countTI >= 3 && countTU >= 3) {
                            ((TthtHnBaseActivity) getActivity()).showSnackBar("Chỉ có thể tối đa có 3 TU và 3 TI.", null);
                            return;
                        }

                        viewRoot.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    DataViewIncludeTuTi dataViewIncludeTuTi = new DataViewIncludeTuTi(getActivity(), listenerTuTiE, tutiListE.size(), MA_BDONG.E, null, null, null, null, Common.IS_BBAN_HIENTRUONG.LAP_NGOAI_HIENTRUONG);
                                    tutiListViewE.add(dataViewIncludeTuTi);
                                    rlIncludeTuTiE.addView(dataViewIncludeTuTi);
                                    indexTuTi = tutiListE.size();
                                    maBdongTuTi = MA_BDONG.E;
                                    Common.runAnimationClickView(rlIncludeTuTiE, R.anim.tththn_twinking_view, 50);
                                    viewRoot.invalidate();

                                    scrollViewTuti.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Rect rectf = new Rect();
                                            tutiListViewE.get(indexTuTi).rlMark.getLocalVisibleRect(rectf);
                                            tutiListViewE.get(indexTuTi).rlMark.getParent().requestChildFocus(tutiListViewE.get(indexTuTi).rlMark, tutiListViewE.get(indexTuTi).rlMark);
                                            scrollViewTuti.scrollTo(0, rectf.top);
                                            scrollViewTuti.smoothScrollTo(0, rectf.top);
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    ((TthtHnBaseActivity) getActivity()).showSnackBar(Common.MESSAGE.ex08.getContent(), e.getMessage(), null);
                                }
                            }
                        });
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    ((TthtHnBaseActivity) getActivity()).showSnackBar(e.getMessage(), null, null);
                }

            }

        });

        btnAddTuTiB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check save
                try {
                    if (indexTuTi != -1) {
                        //check is TU Ti

                        if (maBdongTuTi == MA_BDONG.B) {
                            String message = tutiListViewB.get(indexTuTi).checkCanAddNew();
                            if (TextUtils.isEmpty(message)) {
                                viewRoot.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            DataViewIncludeTuTi dataViewIncludeTuTi = new DataViewIncludeTuTi(getActivity(), listenerTuTiB, tutiListB.size(), MA_BDONG.B, null, null, null, null, Common.IS_BBAN_HIENTRUONG.LAP_NGOAI_HIENTRUONG);
                                            tutiListViewB.add(dataViewIncludeTuTi);
                                            rlIncludeTuTiB.addView(dataViewIncludeTuTi);

                                            indexTuTi = tutiListB.size();
                                            maBdongTuTi = MA_BDONG.B;
                                            Common.runAnimationClickView(rlIncludeTuTiB, R.anim.tththn_twinking_view, 50);
                                            viewRoot.invalidate();

                                            scrollViewTuti.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Rect rectf = new Rect();
                                                    tutiListViewB.get(indexTuTi).rlMark.getLocalVisibleRect(rectf);
                                                    tutiListViewB.get(indexTuTi).rlMark.getParent().requestChildFocus(tutiListViewB.get(indexTuTi).rlMark, tutiListViewB.get(indexTuTi).rlMark);
                                                    scrollViewTuti.scrollTo(0, rectf.top);
                                                    scrollViewTuti.smoothScrollTo(0, rectf.top);
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            ((TthtHnBaseActivity) getActivity()).showSnackBar(Common.MESSAGE.ex08.getContent(), e.getMessage(), null);
                                        }
                                    }
                                });
                            } else {
                                scrollViewTuti.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Rect rectf = new Rect();
                                        tutiListViewB.get(indexTuTi).rlMark.getLocalVisibleRect(rectf);
                                        tutiListViewB.get(indexTuTi).rlMark.getParent().requestChildFocus(tutiListViewB.get(indexTuTi).rlMark, tutiListViewB.get(indexTuTi).rlMark);
                                        scrollViewTuti.scrollTo(0, rectf.top);
                                        scrollViewTuti.smoothScrollTo(0, rectf.top);
                                    }
                                });

                                ((TthtHnBaseActivity) getActivity()).showSnackBar("Cần lưu thông tin Tu Ti Treo thứ " + (indexTuTi + 1) + " trước khi thêm mới Tu Ti Treo: \n" + message, null, null);
                            }
                            return;
                        } else {
                            String message = tutiListViewE.get(indexTuTi).checkCanAddNew();
                            if (TextUtils.isEmpty(message)) {

                                viewRoot.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            DataViewIncludeTuTi dataViewIncludeTuTi = new DataViewIncludeTuTi(getActivity(), listenerTuTiB, tutiListB.size(), MA_BDONG.B, null, null, null, null, Common.IS_BBAN_HIENTRUONG.LAP_NGOAI_HIENTRUONG);
                                            tutiListViewB.add(dataViewIncludeTuTi);
                                            rlIncludeTuTiB.addView(dataViewIncludeTuTi);
                                            indexTuTi = tutiListB.size();
                                            maBdongTuTi = MA_BDONG.B;
                                            Common.runAnimationClickView(rlIncludeTuTiB, R.anim.tththn_twinking_view, 50);
                                            viewRoot.invalidate();

                                            scrollViewTuti.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Rect rectf = new Rect();
                                                    tutiListViewB.get(indexTuTi).rlMark.getLocalVisibleRect(rectf);
                                                    tutiListViewB.get(indexTuTi).rlMark.getParent().requestChildFocus(tutiListViewB.get(indexTuTi).rlMark, tutiListViewB.get(indexTuTi).rlMark);
                                                    scrollViewTuti.scrollTo(0, rectf.top);
                                                    scrollViewTuti.smoothScrollTo(0, rectf.top);
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            ((TthtHnBaseActivity) getActivity()).showSnackBar(Common.MESSAGE.ex08.getContent(), e.getMessage(), null);
                                        }
                                    }
                                });
                            } else {
                                scrollViewTuti.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Rect rectf = new Rect();
                                        tutiListViewE.get(indexTuTi).rlMark.getLocalVisibleRect(rectf);
                                        tutiListViewE.get(indexTuTi).rlMark.getParent().requestChildFocus(tutiListViewE.get(indexTuTi).rlMark, tutiListViewE.get(indexTuTi).rlMark);
                                        scrollViewTuti.scrollTo(0, rectf.top);
                                        scrollViewTuti.smoothScrollTo(0, rectf.top);
                                    }
                                });

                                ((TthtHnBaseActivity) getActivity()).showSnackBar("Cần lưu thông tin Tu Ti Tháo thứ " + (indexTuTi + 1) + " trước khi thêm mới Tu Ti Tháo: \n" + message, null, null);
                            }
                            return;
                        }
                    } else {
                        int countTU = 0;
                        int countTI = 0;
                        for (DataViewIncludeTuTi viewIncludeTuTi : tutiListViewB) {
                            if (Common.IS_TU.findIS_TU(viewIncludeTuTi.tuti.getIS_TU()) == Common.IS_TU.TU) {
                                countTU++;
                            } else {
                                countTI++;
                            }
                        }

                        if (countTI >= 3 && countTU >= 3) {
                            ((TthtHnBaseActivity) getActivity()).showSnackBar("Chỉ có thể tối đa có 3 TU và 3 TI.", null);
                            return;
                        }


                        viewRoot.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    DataViewIncludeTuTi dataViewIncludeTuTi = new DataViewIncludeTuTi(getActivity(), listenerTuTiB, tutiListB.size(), MA_BDONG.B, null, null, null, null, Common.IS_BBAN_HIENTRUONG.LAP_NGOAI_HIENTRUONG);
                                    tutiListViewB.add(dataViewIncludeTuTi);
                                    rlIncludeTuTiB.addView(dataViewIncludeTuTi);
                                    indexTuTi = tutiListB.size();
                                    maBdongTuTi = MA_BDONG.B;
                                    Common.runAnimationClickView(rlIncludeTuTiB, R.anim.tththn_twinking_view, 50);
                                    viewRoot.invalidate();

                                    scrollViewTuti.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Rect rectf = new Rect();
                                            tutiListViewB.get(indexTuTi).rlMark.getLocalVisibleRect(rectf);
                                            tutiListViewB.get(indexTuTi).rlMark.getParent().requestChildFocus(tutiListViewB.get(indexTuTi).rlMark, tutiListViewB.get(indexTuTi).rlMark);
                                            scrollViewTuti.scrollTo(0, rectf.top);
                                            scrollViewTuti.smoothScrollTo(0, rectf.top);
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    ((TthtHnBaseActivity) getActivity()).showSnackBar(Common.MESSAGE.ex08.getContent(), e.getMessage(), null);
                                }
                            }
                        });

                    }
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
        String SO_CTO = (maBdongTuTi == MA_BDONG.B) ? tableChitietCtoB.getSO_CTO() : tableChitietCtoE.getSO_CTO();


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
        String SO_CTO = (maBdongTuTi == MA_BDONG.B) ? tableChitietCtoB.getSO_CTO() : tableChitietCtoE.getSO_CTO();


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
        String SO_CTO = (maBdongTuTi == MA_BDONG.B) ? tableChitietCtoB.getSO_CTO() : tableChitietCtoE.getSO_CTO();


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
        String SO_CTO = (maBdongTuTi == MA_BDONG.B) ? tableChitietCtoB.getSO_CTO() : tableChitietCtoE.getSO_CTO();


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
        String SO_CTO = (maBdongTuTi == MA_BDONG.B) ? tableChitietCtoB.getSO_CTO() : tableChitietCtoE.getSO_CTO();

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
        try {
            //check tu ti
            int countTU = 0;
            int countTI = 0;
            for (DataViewIncludeTuTi viewIncludeTuTi : maBdongTuTi == MA_BDONG.B ? tutiListViewB : tutiListViewE) {
                if (Common.IS_TU.findIS_TU(viewIncludeTuTi.tuti.getIS_TU()) == Common.IS_TU.TU) {
                    countTU++;
                } else {
                    countTI++;
                }
            }

            boolean isTu = Common.IS_TU.findIS_TU(maBdongTuTi == MA_BDONG.B ? tutiListViewB.get(pos).tuti.getIS_TU() : tutiListViewE.get(pos).tuti.getIS_TU()).code;
            if (isTu) {
                if (countTU > 3) {
                    ((TthtHnBaseActivity) getContext()).showSnackBar("Không được quá 3 TU!", null, null);
                    return;
                }
            } else {
                if (countTI > 3) {
                    ((TthtHnBaseActivity) getContext()).showSnackBar("Không được quá 3 TI!", null, null);
                    return;
                }
            }


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
                if (tutiListViewB.get(pos).tuti == null)
                    tutiListViewB.get(pos).tuti = new TABLE_CHITIET_TUTI();
                TABLE_CHITIET_TUTI tutiListViewBOld = (TABLE_CHITIET_TUTI) tutiListViewB.get(pos).tuti.clone();
                tutiListViewB.get(pos).tuti.setCAP_CXAC(-1);
                tutiListViewB.get(pos).tuti.setCAP_DAP(-1);
                tutiListViewB.get(pos).tuti.setID_BBAN_TUTI(-1);
                tutiListViewB.get(pos).tuti.setID_CHITIET_TUTI(-1);
                tutiListViewB.get(pos).tuti.setIS_TU(Common.IS_TU.findIS_TU(Common.IS_TU.findIS_TU(tutiListViewB.get(pos).tuti.getIS_TU()).code).content);
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
                tutiListViewB.get(pos).tuti.setSO_TU_TI(tutiListViewB.get(pos).etSoTuTi.getText().toString());
                tutiListViewB.get(pos).tuti.setNUOC_SX(tutiListViewB.get(pos).etNuocSX.getText().toString());
                tutiListViewB.get(pos).tuti.setSO_TEM_KDINH("");
                tutiListViewB.get(pos).tuti.setNGAY_KDINH(tutiListViewB.get(pos).etNgayKDinh.getText().toString());
                tutiListViewB.get(pos).tuti.setMA_CHI_KDINH("");
                tutiListViewB.get(pos).tuti.setMA_CHI_HOP_DDAY("");

                tutiListViewB.get(pos).tuti.setSO_VONG_THANH_CAI(Integer.parseInt(tutiListViewB.get(pos).etTysoVongTuTi.getText().toString()));
                tutiListViewB.get(pos).tuti.setTYSO_BIEN(tutiListViewB.get(pos).etTysobienTuTi.getText().toString());
                tutiListViewB.get(pos).tuti.setMA_NVIEN(tableBbanCto.getMA_NVIEN());
                tutiListViewB.get(pos).tuti.setSO_BBAN_KDINH_TUTI(-1);
                tutiListViewB.get(pos).tuti.setDIEN_AP(Integer.parseInt(tutiListViewB.get(pos).etDienApTuTi.getText().toString()));
                tutiListViewB.get(pos).tuti.setTY_SO_VONG(Integer.parseInt(tutiListViewB.get(pos).etTysoVongTuTi.getText().toString()));
                tutiListViewB.get(pos).tuti.setIS_BBAN_HIENTRUONG((tutiListViewB.get(pos).tutiLapNgoaiHienTruong.content));
                tutiListViewB.get(pos).tuti.setTINH_TRANG_VAN_HANH(tutiListViewB.get(pos).etTinhTrangVanHanh.getText().toString());
                tutiListViewB.get(pos).tuti.setID_CHITIET_CTO(tableChitietCtoB.getID_CHITIET_CTO());
                tutiListViewB.get(pos).tuti.setID_TABLE_CHITIET_TUTI((int) mSqlDAO.updateORInsertRows(TABLE_CHITIET_TUTI.class, tutiListViewBOld, tutiListViewB.get(indexTuTi).tuti));

                tutiListViewB.get(pos).setHintAfterSave();

            } else {
                if (tutiListViewE.get(pos).tuti == null)
                    tutiListViewE.get(pos).tuti = new TABLE_CHITIET_TUTI();
                TABLE_CHITIET_TUTI tutiListViewEOld = (TABLE_CHITIET_TUTI) tutiListViewE.get(pos).tuti.clone();
                tutiListViewE.get(pos).tuti.setCAP_CXAC(-1);
                tutiListViewE.get(pos).tuti.setCAP_DAP(-1);
                tutiListViewE.get(pos).tuti.setID_BBAN_TUTI(-1);
                tutiListViewE.get(pos).tuti.setID_CHITIET_TUTI(-1);
                tutiListViewE.get(pos).tuti.setIS_TU(Common.IS_TU.findIS_TU(Common.IS_TU.findIS_TU(tutiListViewE.get(pos).tuti.getIS_TU()).code).content);
                tutiListViewE.get(pos).tuti.setLOAI_TU_TI("");
                tutiListViewE.get(pos).tuti.setMA_BDONG(maBdongTuTi.code);
                tutiListViewE.get(pos).tuti.setMA_CHI_HOP_DDAY("");
                tutiListViewE.get(pos).tuti.setMA_CHI_KDINH("");
                tutiListViewE.get(pos).tuti.setMA_CLOAI("");
                tutiListViewE.get(pos).tuti.setMA_DVIQLY(tableBbanCto.getMA_DVIQLY());
                tutiListViewE.get(pos).tuti.setMO_TA("");

                tutiListViewE.get(pos).tuti.setSO_PHA(-1);
                tutiListViewE.get(pos).tuti.setTYSO_DAU("");
                tutiListViewE.get(pos).tuti.setMA_NUOC("");
                tutiListViewE.get(pos).tuti.setMA_HANG("");
                tutiListViewE.get(pos).tuti.setTRANG_THAI(-1);
                tutiListViewE.get(pos).tuti.setSO_TU_TI(tutiListViewE.get(pos).etSoTuTi.getText().toString());
                tutiListViewE.get(pos).tuti.setNUOC_SX(tutiListViewE.get(pos).etNuocSX.getText().toString());
                tutiListViewE.get(pos).tuti.setSO_TEM_KDINH("");
                tutiListViewE.get(pos).tuti.setNGAY_KDINH(tutiListViewE.get(pos).etNgayKDinh.getText().toString());
                tutiListViewE.get(pos).tuti.setMA_CHI_KDINH("");
                tutiListViewE.get(pos).tuti.setMA_CHI_HOP_DDAY("");
                tutiListViewE.get(pos).tuti.setSO_VONG_THANH_CAI(Integer.parseInt(tutiListViewE.get(pos).etTysoVongTuTi.getText().toString()));
                tutiListViewE.get(pos).tuti.setTYSO_BIEN(tutiListViewE.get(pos).etTysobienTuTi.getText().toString());
                tutiListViewE.get(pos).tuti.setMA_NVIEN(tableBbanCto.getMA_NVIEN());
                tutiListViewE.get(pos).tuti.setSO_BBAN_KDINH_TUTI(-1);
                tutiListViewE.get(pos).tuti.setDIEN_AP(Integer.parseInt(tutiListViewE.get(pos).etDienApTuTi.getText().toString()));
                tutiListViewE.get(pos).tuti.setTY_SO_VONG(Integer.parseInt(tutiListViewE.get(pos).etTysoVongTuTi.getText().toString()));
                tutiListViewB.get(pos).tuti.setIS_BBAN_HIENTRUONG((tutiListViewE.get(pos).tutiLapNgoaiHienTruong.content));
                tutiListViewE.get(pos).tuti.setTINH_TRANG_VAN_HANH(tutiListViewE.get(pos).etTinhTrangVanHanh.getText().toString());
                tutiListViewE.get(pos).tuti.setID_CHITIET_CTO(tableChitietCtoE.getID_CHITIET_CTO());
                tutiListViewE.get(pos).tuti.setID_TABLE_CHITIET_TUTI((int) mSqlDAO.updateORInsertRows(TABLE_CHITIET_TUTI.class, tutiListViewEOld, tutiListViewE.get(indexTuTi).tuti));

                tutiListViewE.get(pos).setHintAfterSave();
            }

        } catch (Exception e) {
            String[] collumnCheck = new String[]{
                    TABLE_BBAN_TUTI.table.ID_TABLE_BBAN_TUTI.name()
            };
            String[] valuesCheck = new String[]{
                    String.valueOf(tableBbanTuti.getID_TABLE_BBAN_TUTI())
            };

            int rowdelete = mSqlDAO.deleteRows(TABLE_BBAN_TUTI.class, collumnCheck, valuesCheck);
            tableBbanTuti = null;
            throw new Exception("Lưu thông tin tu ti không thành công. Nội dung lỗi: " + e.getMessage());
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
        String SO_CTO = (maBdongTuTi == MA_BDONG.B) ? tableChitietCtoB.getSO_CTO() : tableChitietCtoE.getSO_CTO();
        String TEN_KHANG = tableBbanCto.getTEN_KHANG();
        String MA_DDO = tableBbanCto.getMA_DDO();
        String MA_TRAM = tableBbanCto.getMA_TRAM();
        Common.LOAI_CTO loaiCto = Common.LOAI_CTO.findLOAI_CTO(maBdongTuTi == MA_BDONG.B ? tableChitietCtoB.getLOAI_CTO() : tableChitietCtoE.getLOAI_CTO());
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
                    SO_TUTI_DRAW = (Common.IS_TU.findIS_TU(tutiListViewB.get(pos).tuti.getIS_TU()).code) ? "SỐ TU" : "SỐ TI" + " TREO: " + tutiListViewB.get(pos).etSoTuTi.getText().toString();
                } else
                    SO_TUTI_DRAW = (Common.IS_TU.findIS_TU(tutiListViewE.get(pos).tuti.getIS_TU()).code) ? "SỐ TU" : "SỐ TI" + " THÁO: " + tutiListViewE.get(pos).etSoTuTi.getText().toString();
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
            isTu = Common.IS_TU.findIS_TU(tutiListViewB.get(pos).tuti.getIS_TU()).code;
        else
            isTu = Common.IS_TU.findIS_TU(tutiListViewE.get(pos).tuti.getIS_TU()).code;


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

        TextView rlMark;

        int index;
        boolean flagChangeData;

//        String stringEtSoTuTi = "", stringEtSoBBKdinh = "",
//                stringEtNuocSX = "", stringEtDienApTuTi = "",
//                stringEtTysobienTuTi = "", stringEtTysoVongTuTi = "",
//                stringEtNgayKDinh = "", stringEtTinhTrangVanHanh = "";

        TABLE_ANH_HIENTRUONG anhTuTi;
        TABLE_ANH_HIENTRUONG anhNhiThu;
        TABLE_ANH_HIENTRUONG anhNiemPhong;

        Common.IS_BBAN_HIENTRUONG tutiLapNgoaiHienTruong;
//        Common.IS_BBAN_HIENTRUONG bbanTuTiLapNgoaiHienTruong;

        TABLE_CHITIET_TUTI tuti;
        MA_BDONG maBdongTuTi;

        private Bitmap bitmapAnhNiemPhong;
        private Bitmap bitmapAnhNhiThu;
        private Bitmap bitmapAnhTuTi;

        public DataViewIncludeTuTi(Context context, ListenerViewInCludeTuTi listener, int indexTuTi, MA_BDONG maBdongTuTi, TABLE_CHITIET_TUTI tableChitietTuti, TABLE_ANH_HIENTRUONG anhTuTi, TABLE_ANH_HIENTRUONG anhNhiThu, TABLE_ANH_HIENTRUONG anhNiemPhong,
//                                   Common.IS_BBAN_HIENTRUONG bbanTuTiLapNgoaiHienTruong,
                                   Common.IS_BBAN_HIENTRUONG tutiLapNgoaiHienTruong) {
            super(context);

            this.listener = listener;
            this.anhTuTi = anhTuTi;
            this.anhNhiThu = anhNhiThu;
            this.anhNiemPhong = anhNiemPhong;
            this.index = indexTuTi;
            this.maBdongTuTi = maBdongTuTi;
            this.tutiLapNgoaiHienTruong = tutiLapNgoaiHienTruong;
//            this.bbanTuTiLapNgoaiHienTruong = bbanTuTiLapNgoaiHienTruong;

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
            rlMark = rowView.findViewById(R.id.rl_mark);


            tuti = tableChitietTuti;

            fillData(tableChitietTuti);

            switch (tutiLapNgoaiHienTruong) {
                case LAP_TU_CMIS:
                    if (MA_BDONG.findMA_BDONGByCode(tuti.getMA_BDONG()) == MA_BDONG.E)
                        enableView(false);
                    else
                        enableView(true);
                    break;
                case LAP_NGOAI_HIENTRUONG:
                    enableView(true);
                    break;
            }
            ;

            clickButton();

            this.addView(rowView);
        }

        private void enableView(boolean enable) {
            etSoTuTi.setEnabled(enable);
            etSoBBKdinh.setEnabled(enable);
            etNuocSX.setEnabled(enable);
            etDienApTuTi.setEnabled(enable);
            etTysobienTuTi.setEnabled(enable);
            etTysoVongTuTi.setEnabled(enable);
            etNgayKDinh.setEnabled(enable);
            etTinhTrangVanHanh.setEnabled(enable);
            btnAnhTuti.setEnabled(enable);
            btnAnhNhiThu.setEnabled(enable);
            btnAnhNiemPhong.setEnabled(enable);
            btnSaveDataTuTi.setEnabled(enable);
            rbTu.setEnabled(enable);
            rbTi.setEnabled(enable);
        }

        private void clickButton() {
            //region ảnh
            btnAnhTuti.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.clickbtnAnhTuti(Common.IS_TU.findIS_TU(tuti.getIS_TU()).code, index);

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
                    listener.clickbtnAnhNhiThu(Common.IS_TU.findIS_TU(tuti.getIS_TU()).code, index);
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

                    listener.clickbtnAnhNiemPhong(Common.IS_TU.findIS_TU(tuti.getIS_TU()).code, index);
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
                            tuti.setIS_TU(Common.IS_TU.TI.content);
                            tvTitle.setText("Thông tin TI " + ((MA_BDONG.findMA_BDONGByCode(tuti.getMA_BDONG()) == MA_BDONG.E) ? "Tháo " : "Treo ") + (index + 1));

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
                            tuti.setIS_TU(Common.IS_TU.TU.content);
                            tvTitle.setText("Thông tin TU " + ((MA_BDONG.findMA_BDONGByCode(tuti.getMA_BDONG()) == MA_BDONG.E) ? "Tháo " : "Treo ") + (index + 1));

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

        private void fillData(TABLE_CHITIET_TUTI tableChitietTuti) {
            if (tableChitietTuti != null) {
                tvTitle.setText("Thông tin " + ((Common.IS_TU.findIS_TU(tableChitietTuti.getIS_TU()).code) ? "TU " : "TI ") + ((MA_BDONG.findMA_BDONGByCode(tableChitietTuti.getMA_BDONG()) == MA_BDONG.E) ? "Tháo " : "Treo ") + (index + 1));
                rbTu.setChecked(Common.IS_TU.findIS_TU(tableChitietTuti.getIS_TU()).code);
                rbTi.setChecked(!rbTu.isChecked());


                etSoTuTi.setText(tableChitietTuti.getSO_TU_TI());
                etSoBBKdinh.setText(tableChitietTuti.getSO_BBAN_KDINH_TUTI() + "");
                etNuocSX.setText(tableChitietTuti.getNUOC_SX());
                etDienApTuTi.setText(tableChitietTuti.getDIEN_AP() + "");
                etTysobienTuTi.setText(tableChitietTuti.getTYSO_BIEN());
                etTysoVongTuTi.setText(tableChitietTuti.getTY_SO_VONG() + "");
                etNgayKDinh.setText(tableChitietTuti.getCAP_CXAC() + "");
                etTinhTrangVanHanh.setText(tableChitietTuti.getTINH_TRANG_VAN_HANH());


                etSoTuTi.setHint(tableChitietTuti.getSO_TU_TI());
                etSoBBKdinh.setHint(tableChitietTuti.getSO_BBAN_KDINH_TUTI() + "");
                etNuocSX.setHint(tableChitietTuti.getNUOC_SX());
                etDienApTuTi.setHint(tableChitietTuti.getDIEN_AP() + "");
                etTysobienTuTi.setHint(tableChitietTuti.getTYSO_BIEN());
                etTysoVongTuTi.setHint(tableChitietTuti.getTY_SO_VONG() + "");
                etNgayKDinh.setHint(tableChitietTuti.getCAP_CXAC() + "");
                etTinhTrangVanHanh.setHint(tableChitietTuti.getTINH_TRANG_VAN_HANH());


//            String MA_DVIQLY = tuti.getMA_DVIQLY();
//            String MA_TRAM = tuti.getMA_TRAM();
//            String SO_CTO = (maBdongTuTi == MA_BDONG.B) ? tableChitietCtoB.getSO_CTO() : tableChitietCtoE.getSO_CTO();

                String pathURICapturedAnh = null;

                boolean isTu = Common.IS_TU.findIS_TU(tuti.getIS_TU()).code;


                bitmapAnhTuTi = getBitmap(anhTuTi, isTu);
                ivAnhTuti.setImageBitmap(bitmapAnhTuTi);

                bitmapAnhNhiThu = getBitmap(anhNhiThu, isTu);
                ivAnhNhiThu.setImageBitmap(bitmapAnhNhiThu);

                bitmapAnhNiemPhong = getBitmap(anhNiemPhong, isTu);
                ivAnhNiemPhong.setImageBitmap(bitmapAnhNiemPhong);
            } else {
                //default
                tuti = new TABLE_CHITIET_TUTI();
                tuti.setIS_TU(Common.IS_TU.TU.content);
                tuti.setMA_BDONG(maBdongTuTi.code);

                tvTitle.setText("Thông tin " + ((Common.IS_TU.findIS_TU(tuti.getIS_TU()).code) ? "TU " : "TI ") + ((MA_BDONG.findMA_BDONGByCode(tuti.getMA_BDONG()) == MA_BDONG.E) ? "Tháo " : "Treo ") + (index + 1));
                rbTu.setChecked(Common.IS_TU.findIS_TU(tuti.getIS_TU()).code);
                rbTi.setChecked(!rbTu.isChecked());
            }

        }

        private Bitmap getBitmap(TABLE_ANH_HIENTRUONG anhHientruong, boolean isTu) {
            if (anhHientruong == null)
                return null;

            if (TextUtils.isEmpty(anhHientruong.getTEN_ANH()))
                return null;

            String TEN_ANH = anhHientruong.getTEN_ANH();
            String pathURICapturedAnh = Common.getRecordDirectoryFolder(isTu ? Common.FOLDER_NAME.FOLDER_ANH_TU.name() : Common.FOLDER_NAME.FOLDER_ANH_TI.name()) + "/" + TEN_ANH;
            //get bitmap tu URI
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(pathURICapturedAnh, options);
            return bitmap;
        }


        public void setBitmap(Common.TYPE_IMAGE typeImage, Bitmap bitmap) {
            switch (typeImage) {
                case IMAGE_CONG_TO:
                    break;
                case IMAGE_CONG_TO_NIEM_PHONG:
                    break;
                case IMAGE_TUTI:
                    bitmapAnhTuTi = bitmap;
                    ivAnhTuti.setImageBitmap(bitmap);
                    break;
                case IMAGE_MACH_NHI_THU_TUTI:
                    bitmapAnhNhiThu = bitmap;
                    ivAnhNhiThu.setImageBitmap(bitmap);
                    break;
                case IMAGE_NIEM_PHONG_TUTI:
                    bitmapAnhNiemPhong = bitmap;
                    ivAnhNiemPhong.setImageBitmap(bitmap);
                    break;
            }
        }

        public String checkCanSave() {
            //check bitmap
            String messageSave = "";
            Bitmap bitmap = null;
            bitmap = (ivAnhNiemPhong.getDrawable() == null) ? null : ((BitmapDrawable) ivAnhNiemPhong.getDrawable()).getBitmap();
            messageSave = (bitmap != null) ? messageSave : "Vui lòng chụp ảnh niêm phong!";
            messageSave = (bitmapAnhNiemPhong != null) ? messageSave : "Vui lòng lưu thông tin TU TI!";
            bitmap = (ivAnhNhiThu.getDrawable() == null) ? null : ((BitmapDrawable) ivAnhNhiThu.getDrawable()).getBitmap();
            messageSave = (bitmap != null) ? messageSave : "Vui lòng chụp ảnh nhị thứ!";
            messageSave = (bitmapAnhNhiThu != null) ? messageSave : "Vui lòng lưu thông tin TU TI!";
            bitmap = (ivAnhTuti.getDrawable() == null) ? null : ((BitmapDrawable) ivAnhTuti.getDrawable()).getBitmap();
            messageSave = (bitmap != null) ? messageSave : "Vui lòng chụp ảnh tu ti!";
            messageSave = (bitmapAnhTuTi != null) ? messageSave : "Vui lòng lưu thông tin TU TI!";

//            boolean isHasChangeDataEditText = etSoTuTi.getText().toString().equalsIgnoreCase(etSoTuTi.getHint().toString())
//                    || etSoBBKdinh.getText().toString().equalsIgnoreCase(etSoBBKdinh.getHint().toString())
//                    || etNuocSX.getText().toString().equalsIgnoreCase(etNuocSX.getHint().toString())
//                    || etDienApTuTi.getText().toString().equalsIgnoreCase(etDienApTuTi.getHint().toString())
//                    || etTysobienTuTi.getText().toString().equalsIgnoreCase(etTysobienTuTi.getHint().toString())
//                    || etTysoVongTuTi.getText().toString().equalsIgnoreCase(etTysoVongTuTi.getHint().toString())
//                    || etNgayKDinh.getText().toString().equalsIgnoreCase(etNgayKDinh.getHint().toString())
//                    || etTinhTrangVanHanh.getText().toString().equalsIgnoreCase(etTinhTrangVanHanh.getHint().toString());


//            messageSave = isHasChangeDataEditText ? "Cần lưu thông tin Tu hoặc Ti trước" : messageSave;

            boolean isHasOkDataEditText = (TextUtils.isEmpty(etSoTuTi.getText().toString()))
                    || (TextUtils.isEmpty(etNuocSX.getText().toString()))
                    || (TextUtils.isEmpty(etDienApTuTi.getText().toString()))
                    || (TextUtils.isEmpty(etTysobienTuTi.getText().toString()))
                    || (TextUtils.isEmpty(etTysoVongTuTi.getText().toString()))
                    || (TextUtils.isEmpty(etNgayKDinh.getText().toString()));
            messageSave = isHasOkDataEditText ? "Không để trống các thông tin bắt buộc (*)" : messageSave;

            return messageSave;
        }

        public void setHintAfterSave() {
            String stringEtSoTuTi = etSoTuTi.getText().toString();
            String stringEtSoBBKdinh = etSoBBKdinh.getText().toString();
            String stringEtNuocSX = etNuocSX.getText().toString();
            String stringEtDienApTuTi = etDienApTuTi.getText().toString();
            String stringEtTysobienTuTi = etTysobienTuTi.getText().toString();
            String stringEtTysoVongTuTi = etTysoVongTuTi.getText().toString();
            String stringEtNgayKDinh = etNgayKDinh.getText().toString();
            String stringEtTinhTrangVanHanh = etTinhTrangVanHanh.getText().toString();

            etSoTuTi.setHint(stringEtSoTuTi);
            etSoBBKdinh.setHint(stringEtSoBBKdinh);
            etNuocSX.setHint(stringEtNuocSX);
            etDienApTuTi.setHint(stringEtDienApTuTi);
            etTysobienTuTi.setHint(stringEtTysobienTuTi);
            etTysoVongTuTi.setHint(stringEtTysoVongTuTi);
            etNgayKDinh.setHint(stringEtNgayKDinh);
            etTinhTrangVanHanh.setHint(stringEtTinhTrangVanHanh);
        }

        public String checkCanAddNew() {
            //nếu từ lập từ CMIS và là tháo thì không cần kiểm tra
            if (tutiLapNgoaiHienTruong == Common.IS_BBAN_HIENTRUONG.LAP_TU_CMIS && MA_BDONG.findMA_BDONGByCode(tuti.getMA_BDONG()) == MA_BDONG.E)
                return "";

            //check bitmap
            String messageSave = "";
            Bitmap bitmap = null;
            bitmap = (ivAnhNiemPhong.getDrawable() == null) ? null : ((BitmapDrawable) ivAnhNiemPhong.getDrawable()).getBitmap();
            messageSave = (bitmap != null) ? messageSave : "Vui lòng chụp ảnh niêm phong!";
            messageSave = (bitmapAnhNiemPhong != null) ? messageSave : "Vui lòng lưu thông tin TU TI!";
            bitmap = (ivAnhNhiThu.getDrawable() == null) ? null : ((BitmapDrawable) ivAnhNhiThu.getDrawable()).getBitmap();
            messageSave = (bitmap != null) ? messageSave : "Vui lòng chụp ảnh nhị thứ!";
            messageSave = (bitmapAnhNhiThu != null) ? messageSave : "Vui lòng lưu thông tin TU TI!";
            bitmap = (ivAnhTuti.getDrawable() == null) ? null : ((BitmapDrawable) ivAnhTuti.getDrawable()).getBitmap();
            messageSave = (bitmap != null) ? messageSave : "Vui lòng chụp ảnh tu ti!";
            messageSave = (bitmapAnhTuTi != null) ? messageSave : "Vui lòng lưu thông tin TU TI!";


            boolean isHasChangeDataEditText = (!etSoTuTi.getText().toString().equalsIgnoreCase(etSoTuTi.getHint().toString()))
                    || (!etSoBBKdinh.getText().toString().equalsIgnoreCase(etSoBBKdinh.getHint().toString()))
                    || (!etNuocSX.getText().toString().equalsIgnoreCase(etNuocSX.getHint().toString()))
                    || (!etDienApTuTi.getText().toString().equalsIgnoreCase(etDienApTuTi.getHint().toString()))
                    || (!etTysobienTuTi.getText().toString().equalsIgnoreCase(etTysobienTuTi.getHint().toString()))
                    || (!etTysoVongTuTi.getText().toString().equalsIgnoreCase(etTysoVongTuTi.getHint().toString()))
                    || (!etNgayKDinh.getText().toString().equalsIgnoreCase(etNgayKDinh.getHint().toString()))
                    || (!etTinhTrangVanHanh.getText().toString().equalsIgnoreCase(etTinhTrangVanHanh.getHint().toString()));


            messageSave = isHasChangeDataEditText ? "Cần lưu thông tin Tu hoặc Ti trước" : messageSave;
            return messageSave;
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
