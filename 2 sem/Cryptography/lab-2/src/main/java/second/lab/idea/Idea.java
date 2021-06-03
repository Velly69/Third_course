package second.lab.idea;

import second.lab.bits.BitArray;
import java.util.ArrayList;
import java.util.List;


/**
 * https://www.opennet.ru/docs/RUS/inet_book/6/idea_647.html
 * https://ru.wikipedia.org/wiki/IDEA
 * https://xakep.ru/2016/06/30/crypto-part4/#toc04.
 * https://intuit.ru/studies/courses/28/28/lecture/20414?page=2
 */

public class Idea {
    private Key originalKey;
    private List<BitArray> encryptionKeys = new ArrayList<>();
    private List<BitArray> decryptionKeys = new ArrayList<>();

    List<TextBlock> encryptedBlocks = new ArrayList<>();
    StringBuilder stringOutput = new StringBuilder();

    public Idea(Key originalKey) {
        this.originalKey = originalKey;
        generateAllSubkeys();
    }

    private void generateAllSubkeys() {
        Key nextKey = originalKey;

        for(int i = 0; i < 6; i++) {
            encryptionKeys.addAll(nextKey.getSubkeys());
            nextKey = nextKey.generateNextKey();
        }
        encryptionKeys.addAll(nextKey.getHalfOfSubkeys());

        //generate decryption keys
        decryptionKeys.add(encryptionKeys.get(48).invert());
        decryptionKeys.add(encryptionKeys.get(49).twosComplement());
        decryptionKeys.add(encryptionKeys.get(50).twosComplement());
        decryptionKeys.add(encryptionKeys.get(51).invert());

        for(int i = 7; i >= 0; i--) {
            decryptionKeys.add(encryptionKeys.get(i * 6 + 4));
            decryptionKeys.add(encryptionKeys.get(i * 6 + 5));
            decryptionKeys.add(encryptionKeys.get(i * 6 + 0).invert());
            if (i == 0) {
                decryptionKeys.add(encryptionKeys.get(i * 6 + 1).twosComplement());
                decryptionKeys.add(encryptionKeys.get(i * 6 + 2).twosComplement());
            } else {
                decryptionKeys.add(encryptionKeys.get(i * 6 + 2).twosComplement());
                decryptionKeys.add(encryptionKeys.get(i * 6 + 1).twosComplement());
            }
            decryptionKeys.add(encryptionKeys.get(i * 6 + 3).invert());
        }
    }

    private TextBlock compute(TextBlock inputBlock, List<BitArray> keys) {
        Round round = new Round();
        HalfRound halfRound = new HalfRound();

        TextBlock resultBlock = inputBlock;
        for (int i = 0; i < 8;i++) {
            resultBlock = round.encrypt(resultBlock, keys.get(i * 6), keys.get(i * 6 + 1),
                    keys.get(i * 6 + 2), keys.get(i * 6 + 3), keys.get(i * 6 + 4),
                    keys.get(i * 6 + 5));
        }
        resultBlock = halfRound.encrypt(resultBlock, keys.get(48), keys.get(49), keys.get(50), keys.get(51));
        return resultBlock;
    }

    private TextBlock encryptTextBlock(TextBlock inputBlock) {
        return compute(inputBlock, encryptionKeys);
    }

    private TextBlock decryptTextBlock(TextBlock inputBlock) {
        return compute(inputBlock, decryptionKeys);
    }

    public List<TextBlock> encrypt(String message) {
        List<TextBlock> blocks =  new StringToTextBlockConvert().convert(message);
        //encrypting
        List<TextBlock> encryptedBlocks = new ArrayList<>();
        for(TextBlock blockToEncrypt : blocks) {
            encryptedBlocks.add(encryptTextBlock(blockToEncrypt));
        }
        return encryptedBlocks;
    }

    public String decrypt(List<TextBlock> encryptedBlocks) {
        StringBuilder stringOutput = new StringBuilder();
        for(TextBlock blockToDecrypt : encryptedBlocks) {
            TextBlock decryptedBlock = decryptTextBlock(blockToDecrypt);
            stringOutput.append(decryptedBlock.getBitArray().toASCII());
        }
        return stringOutput.toString();
    }

    public static void main(String[] args) {
        String string1 =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                        "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis " +
                        "nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                        "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu " +
                        "fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa " +
                        "qui officia deserunt mollit anim id est laborum";
        Key key = new Key();
        key.setK(0, 0x050c);
        key.setK(1, 0x0a0b);
        key.setK(2, 0x00f0);
        key.setK(3, 0x0e00);
        key.setK(4, 0x0501);
        key.setK(5, 0x0103);
        key.setK(6, 0x010d);
        key.setK(7, 0x00cd);
        Idea idea = new Idea(key);
        String res = idea.decrypt(idea.encrypt(string1)).trim();
    }
}
