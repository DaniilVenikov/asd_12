import java.lang.reflect.Array;

class NativeCache<T>
{
    public int size;
    public String [] slots;
    public T [] values;
    public int [] hits;
    private static final int STEP = 3;

    public NativeCache(int size, Class clazz) {
        this.size = size;
        slots = new String[size];
        values = (T[]) Array.newInstance(clazz, this.size);
        hits = new int[size];
    }

    public int hashFun(String key)
    {
        int sum = 0;
        for(int i = 0; i < key.length(); i++) {
            sum += key.charAt(i);
        }

        return sum % size;
    }

    public int seekSlot(String value)
    {
        int firstIndex = hashFun(value);
        int resultIndex = firstIndex;

        do {
            if (slots[resultIndex] == null) {
                return resultIndex;
            }
            resultIndex = (resultIndex + STEP) % size;
        } while (resultIndex != firstIndex);

        return -1;
    }

    public int put(String key, T value) {
        int index = seekSlot(key);

        if (index == -1) {
            int indexMinElement = findIndexMinElement();
            elementDisplacement(indexMinElement);
            index = seekSlot(key);
        }

        slots[index] = key;
        values[index] = value;
        return index;
    }

    public boolean isKey(String key)
    {
        for (String slot : slots) {
            if (slot != null && slot.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public T get(String key)
    {
        int index = hashFun(key);
        int resultIndex = index;

        for (int i = 0; i < size; i++) {
            if (slots[resultIndex] != null && slots[resultIndex].equals(key)) {
                hits[index]++;
                return values[resultIndex];
            }
            resultIndex = (resultIndex + STEP) % size;
        }

        return null;
    }

    private int findIndexMinElement() {
        int minElement = hits[0];
        int minIndex = 0;

        for (int i = 1; i < hits.length; i++) {
            if (hits[i] < minElement) {
                minElement = hits[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    private void elementDisplacement(int index) {
        slots[index] = null;
        values[index] = null;
        hits[index] = 0;
    }
}
