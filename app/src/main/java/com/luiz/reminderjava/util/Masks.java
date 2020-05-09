package com.luiz.reminderjava.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Masks {
    private static final String CPFMask = "###.###.###-##";
    private static final String RGMask = "##.###.###-#";
    private static final String TelMask = "(##) ####-####";
    private static final String CelMask = "(##) #####-####";
    private static final String DateMask = "##/##/####";
    private static final String HoraMask = "##:##";

    public static String unmask(String s) {
        return s.replaceAll("[^0-9]*", "").replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "").replaceAll("[(]", "").replaceAll("[ ]", "").replaceAll("[:]", "").replaceAll("[)]", "");
    }

    private static String getDefaultMask(String str) {
        String defaultMask = CPFMask;
        if (str.length() == 9) {
            defaultMask = RGMask;
        } else if (str.length() == 8) {
            defaultMask = DateMask;
        } else if (str.length() == 10) {
            defaultMask = TelMask;
        } else if (str.length() == 11) {
            defaultMask = CelMask;
        } else if (str.length() == 4) {
            defaultMask = HoraMask;
        }
        return CPFMask;
    }

    public static TextWatcher insert(final EditText editText, final MaskType maskType) {
        return new TextWatcher() {

            boolean isUpdating;
            String oldValue = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = Masks.unmask(s.toString());
                String mask;
                switch (maskType) {
                    case CPF:
                        mask = CPFMask;
                        break;
                    case RG:
                        mask = RGMask;
                        break;
                    case Telefone:
                        mask = TelMask;
                        break;
                    case Data:
                        mask = DateMask;
                        break;
                    case Hora:
                        mask = HoraMask;
                        break;
                    case Cel:
                        mask = CelMask;
                        break;
                    default:
                        mask = getDefaultMask(value);
                        break;
                }
                String maskAux = "";
                if (isUpdating) {
                    oldValue = value;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if ((m != '#' && value.length() > oldValue.length()) || (m != '#' && value.length() < oldValue.length() && value.length() != i)) {
                        maskAux += m;
                        continue;
                    }
                    try {
                        maskAux += value.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                editText.setText(maskAux);
                editText.setSelection(maskAux.length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }
}
