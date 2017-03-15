package br.com.tiagohs.popmovies.ui.view.fragment;

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

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.FilterValuesDTO;
import br.com.tiagohs.popmovies.model.dto.SortByItemDTO;
import br.com.tiagohs.popmovies.ui.callbacks.FiltersMoviesCallbacks;
import br.com.tiagohs.popmovies.util.LocaleUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterDialogFragment extends DialogFragment {

    private static final int MIN_YEAR = Calendar.getInstance().get(Calendar.YEAR) + 5;
    private static final int MAX_YEAR = 1890;
    private static final int MIN_NOTA = 0;
    private static final int MAX_NOTA = 10;

    @BindView(R.id.adult_checkbox)              CheckBox mAdultCheckBox;
    @BindView(R.id.ordenar_por_spinner)         Spinner mSortBySpinner;
    @BindView(R.id.ano_lancamento_spinner)      Spinner mAnoLancamentoSpinner;
    @BindView(R.id.data_inicial)                EditText mDataInicial;
    @BindView(R.id.data_final)                  EditText mDataFinal;
    @BindView(R.id.nota_comunidade_seekbar)     CrystalSeekbar mNotaComunidade;
    @BindView(R.id.min_nota_text_view)          TextView mNotaInicial;

    private SimpleDateFormat mDateFormatter;
    private DatePickerDialog mFromDatePickerDialog;
    private DatePickerDialog mToDatePickerDialog;

    private FilterValuesDTO mFilterValuesDTO;
    private FiltersMoviesCallbacks mFiltersMoviesCallbacks;

    public static FilterDialogFragment newInstance() {
        Bundle bundle = new Bundle();

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

        setStyle(STYLE_NORMAL, getActivity().getApplicationInfo().theme);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.filter_dialog_title))
                .setPositiveButton(R.string.btn_aplicar,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                             mFiltersMoviesCallbacks.onFilterChanged(mFilterValuesDTO);
                            }
                        }
                )
                .setNegativeButton(R.string.btn_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .setNeutralButton(R.string.btn_reset, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mFiltersMoviesCallbacks.onFilterReset();
                    }
                });

        createView(alertDialog);

        return alertDialog.create();
    }

    private void createView(AlertDialog.Builder alertDialog) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_filter_dialog, null);

        ButterKnife.bind(this, view);
        alertDialog.setView(view);
        configurateView();
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
        mAnoLancamentoSpinner.setAdapter(createArrayAdapter());
        mAnoLancamentoSpinner.setOnItemSelectedListener(createOnItemSelectedListener());
    }

    private AdapterView.OnItemSelectedListener createOnItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) adapterView.getItemAtPosition(i);
                mFilterValuesDTO.setReleaseYear(item.equals(getString(R.string.btn_nenhum)) ? null : item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
    }

    private ArrayAdapter createArrayAdapter() {
        List<String> years = new ArrayList<>();
        years.add(getString(R.string.btn_nenhum));

        for (int cont = MIN_YEAR; cont >= MAX_YEAR; cont--)
            years.add(String.valueOf(cont));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, years);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return dataAdapter;
    }

    private void configureSeekbarNota() {
        mNotaComunidade.setDataType(CrystalRangeSeekbar.DataType.INTEGER)
                .setMinValue(MIN_NOTA)
                .setMaxValue(MAX_NOTA)
                .setSteps(1)
                .apply();

        mNotaComunidade.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                mNotaInicial.setText(String.valueOf(value));
                mFilterValuesDTO.setVoteAverageGte(String.valueOf(value));
            }
        });

        mNotaComunidade.setOnSeekbarFinalValueListener(new OnSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number value) {
                mFilterValuesDTO.setVoteAverageGte(String.valueOf(value));
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
