package br.com.tiagohs.popmovies.view.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.FilterValuesDTO;
import br.com.tiagohs.popmovies.model.dto.SortByItemDTO;
import br.com.tiagohs.popmovies.util.LocaleUtils;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.view.callbacks.FiltersMoviesCallbacks;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterDialogFragment extends DialogFragment {
    private static final String ARG_TYPE_LIST = "typeList";

    @BindView(R.id.adult_checkbox)
    CheckBox mAdultCheckBox;

    @BindView(R.id.ordenar_por_spinner)
    Spinner mSortBySpinner;

    @BindView(R.id.ano_lancamento_spinner)
    Spinner mAnoLancamentoSpinner;

    @BindView(R.id.data_inicial)
    EditText mDataInicial;

    @BindView(R.id.data_final)
    EditText mDataFinal;

    @BindView(R.id.nota_comunidade_seekbar)
    CrystalRangeSeekbar mNotaComunidade;

    @BindView(R.id.min_nota_text_view)
    TextView mNotaInicial;

    @BindView(R.id.max_nota_text_view)
    TextView mNotaFinal;

    private ListType mTypeList;
    private SimpleDateFormat mDateFormatter;
    private DatePickerDialog mFromDatePickerDialog;
    private DatePickerDialog mToDatePickerDialog;

    private FilterValuesDTO mFilterValuesDTO;
    private FiltersMoviesCallbacks mFiltersMoviesCallbacks;

    public static FilterDialogFragment newInstance(ListType typeList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_TYPE_LIST, typeList);

        FilterDialogFragment filterDialogFragment = new FilterDialogFragment();
        filterDialogFragment.setArguments(bundle);

        return filterDialogFragment;
    }

    public FilterDialogFragment() {
        mFilterValuesDTO = new FilterValuesDTO();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFiltersMoviesCallbacks = (FiltersMoviesCallbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFiltersMoviesCallbacks = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTypeList = (ListType) getArguments().getSerializable(ARG_TYPE_LIST);

        setStyle(STYLE_NORMAL, getActivity().getApplicationInfo().theme);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b=  new  AlertDialog.Builder(getActivity())
                .setTitle("Filtrar")
                .setPositiveButton("Aplicar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                mFiltersMoviesCallbacks.onFilterChanged(mFilterValuesDTO);
                            }
                        }
                )
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .setNeutralButton("Resetar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        resetValues();
                        mFiltersMoviesCallbacks.onFilterChanged(mFilterValuesDTO);
                    }
                });

        View v = createView();

        ButterKnife.bind(this, v);
        b.setView(v);

        configurateView();

        return b.create();
    }

    private void resetValues() {
        mFilterValuesDTO = new FilterValuesDTO();
    }

    private View createView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();

       switch (mTypeList) {
           case MOVIES:
               return inflater.inflate(R.layout.fragment_filter_dialog, null);

           default:
               return inflater.inflate(R.layout.fragment_filter_dialog, null);
       }
    }

    private void configurateView() {

        configureAdultCheckBox();
        configureSortBySnipper();
        configureAnoSnipper();
        configureSeekbarNota();
        configureDataLancamento();
    }

    private void configureAdultCheckBox() {
        mAdultCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mFilterValuesDTO.setIncludeAdult(b);
            }
        });
    }

    private void configureSortBySnipper() {
        ArrayAdapter<SortByItemDTO> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, SortByItemDTO.getItemsDefault(getActivity()));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSortBySpinner.setAdapter(dataAdapter);
        mSortBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SortByItemDTO item = (SortByItemDTO) adapterView.getItemAtPosition(i);
                mFilterValuesDTO.setSortBy(item.getValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void configureAnoSnipper() {
        List<String> years = new ArrayList<>();

        years.add("Nenhum");
        for (int cont = Calendar.getInstance().get(Calendar.YEAR) + 5; cont >= 1990; cont--)
            years.add(String.valueOf(cont));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, years);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mAnoLancamentoSpinner.setAdapter(dataAdapter);
        mAnoLancamentoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) adapterView.getItemAtPosition(i);
                mFilterValuesDTO.setReleaseYear(item.equals("Nenhum") ? null : item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void configureSeekbarNota() {
        mNotaComunidade.setDataType(CrystalRangeSeekbar.DataType.INTEGER)
                .setMinValue(0)
                .setMaxValue(10)
                .setSteps(1)
                .apply();

        mNotaComunidade.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                mNotaInicial.setText(String.valueOf(minValue));
                mNotaFinal.setText(String.valueOf(maxValue));

                mFilterValuesDTO.setVoteAverageGte(String.valueOf(minValue));
                mFilterValuesDTO.setVoteAverageLte(String.valueOf(maxValue));
            }
        });

        mNotaComunidade.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                mFilterValuesDTO.setVoteAverageGte(String.valueOf(minValue));
                mFilterValuesDTO.setVoteAverageLte(String.valueOf(maxValue));
            }
        });

    }

    private void configureDataLancamento() {
        mDateFormatter = new SimpleDateFormat("dd-MM-yyyy", LocaleUtils.getLocaleAtual());
        Calendar newCalendar = Calendar.getInstance();

        mFromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mDataInicial.setText(mDateFormatter.format(newDate.getTime()));
                mFilterValuesDTO.setPrimaryRelaseDateGteByDate(newDate);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        mDataInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFromDatePickerDialog.show();
            }
        });

        mToDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mDataFinal.setText(mDateFormatter.format(newDate.getTime()));
                mFilterValuesDTO.setPrimaryRelaseDateLteByDate(newDate);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        mDataFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mToDatePickerDialog.show();
            }
        });

    }

}
