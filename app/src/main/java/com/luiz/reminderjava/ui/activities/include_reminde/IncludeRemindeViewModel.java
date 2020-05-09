package com.luiz.reminderjava.ui.activities.include_reminde;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.luiz.reminderjava.api.responses.NoteResponse;
import com.luiz.reminderjava.ui.activities.main.MainActivity;
import com.luiz.reminderjava.ui.base.BaseViewModel;
import com.luiz.reminderjava.util.Utils;
import com.mlykotom.valifi.ValiFiForm;
import com.mlykotom.valifi.fields.ValiFieldText;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class IncludeRemindeViewModel extends BaseViewModel {

    public final ValiFieldText title = new ValiFieldText().addNotEmptyValidator("Campo obrigatório!");
    public final ValiFieldText date = new ValiFieldText().addNotEmptyValidator("Campo obrigatório!").addExactLengthValidator("Data inválida!", 10);
    public final ValiFieldText hour = new ValiFieldText().addNotEmptyValidator("Campo obrigatório!").addExactLengthValidator("Hora inválida!", 5);
    public final ValiFieldText description = new ValiFieldText().addNotEmptyValidator("Campo obrigatório!");
    public final MutableLiveData<Boolean> isNotified = new MutableLiveData<>();
    public final ValiFiForm form = new ValiFiForm(title, description, date, hour);

    private RequestBody createBody() {

        String dateFinal = Utils.convertDateFormat(date.getValue() + " " + hour.getValue(), "dd/MM/yyyy HH:mm", "yyyy-MM-dd HH:mm");

        JsonObject json = new JsonObject();
        json.addProperty("title", title.getValue());
        json.addProperty("description", description.getValue());
        json.addProperty("date", dateFinal);
        json.addProperty("is_notified", isNotified.getValue() != null && isNotified.getValue());

        return RequestBody.create(MediaType.parse("application/json"), json.toString());
    }

    public void registerReminde() {
        this.beforeRequest();

        disposable.add(
                apiRepository.registerNote(createBody())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<NoteResponse>() {

                            @Override
                            public void onSuccess(NoteResponse noteResponse) {
                                afterRequest();

                                redirectToHome();
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                afterRequest(e);
                            }
                        })
        );
    }

    private void redirectToHome() {
        Toast.makeText(context, "Lembrete criado com sucesso!", Toast.LENGTH_SHORT).show();
        Utils.startActivity(context, MainActivity.class);
    }

    @Override
    protected void onCleared() {
        form.destroy();
        disposable.clear();

        super.onCleared();
    }
}
