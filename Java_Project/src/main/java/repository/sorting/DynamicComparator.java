package repository.sorting;

import model.Sale;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;

public class DynamicComparator implements Comparator<Object> {

    private String type;

    public DynamicComparator(String type) {

        this.type = type.substring(0, 1).toUpperCase() + type.substring(1);
    }

    @Override
    public int compare(Object o1, Object o2) {

        String s;
        String s1;

        switch (this.type) {

            case "Bike" -> {

                s = ((Sale) o1).getBike().getName();
                s1 = ((Sale) o2).getBike().getName();

                return s.compareTo(s1);
            }

            case "Customer" -> {

                s = ((Sale) o1).getCustomer().getFirstName();
                s1 = ((Sale) o2).getCustomer().getFirstName();

                return s.compareTo(s1);
            }

            default -> {

                try {

                    Method m = o1.getClass().getMethod("get" + type);

                    Comparable<Object> comparable0 = (Comparable<Object>) m.invoke(o1);
                    Comparable<Object> comparable1 = (Comparable<Object>) m.invoke(o2);

                    return comparable0.compareTo(comparable1);

                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return -1;
    }
}
