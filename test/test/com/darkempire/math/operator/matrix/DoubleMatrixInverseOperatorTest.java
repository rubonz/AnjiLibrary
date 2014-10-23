package test.com.darkempire.math.operator.matrix;

import com.darkempire.math.operator.matrix.PseudoInverseMethodType;
import com.darkempire.math.struct.matrix.DoubleFixedMatrix;
import com.darkempire.math.struct.matrix.DoubleMatrix;
import junit.framework.Assert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static test.util.DoubleMatrixMatcher.*;
import static com.darkempire.math.operator.matrix.DoubleMatrixInverseOperator.*;

/**
 * Created by siredvin on 23.10.14.
 *
 * @author siredvin
 */
public class DoubleMatrixInverseOperatorTest extends Assert {

    @Test
    public void testPseudoInverseGri() {
        DoubleMatrix a, atest;
        a = DoubleFixedMatrix.createInstance(8, 9, new double[]{0.3471137752452498, 0.04928290587194728, 0.2861960175837567, 0.11645828436896866, 0.13998887539984728, 0.29876876781315864, 0.15836255043633507, 0.595995437318177, 0.07096663914247758, 0.5688961761470526, 0.7330570510681094, 0.7088143416826275, 0.7448966216263518, 0.6604889502046354, 0.8383101115714544, 0.4802020658955115, 0.33226301996514673, 0.33655493236262424, 0.8154029664206408, 0.984984144157132, 0.8327678885538514, 0.891801838294358, 0.680349893086467, 0.2528383676232995, 0.3458339781083505, 0.8573348885853801, 0.6476557947194818, 0.02956614736578267, 0.7266917803544971, 0.8375636187745642, 0.17978244485221861, 0.6409127356510804, 0.45020063053155057, 0.20987444814113831, 0.20555911661528348, 0.7653353628528838, 0.9160872185019945, 0.19740370110902583, 0.5021423418984897, 0.27503655726943976, 0.6326941047872703, 0.4921342765654333, 0.9867396776554209, 0.7204036313526223, 0.6606549127020535, 0.26375019521151755, 0.7830027688666609, 0.6030450879668138, 0.23869880704329116, 0.7187030179302787, 0.13729372325546674, 0.16556104263874527, 0.3546157248261037, 0.31259578275194266, 0.6636223820189321, 0.5786211237129865, 0.24281254743778047, 0.9367642105286647, 0.24966848174362244, 0.03864447509786462, 0.1959623698125399, 0.587471434887976, 0.7755126335023235, 0.1625305446337575, 0.6636378617521836, 0.0813818200784765, 0.3582396804183361, 0.39058114364818897, 0.5683612248669992, 0.4279113100243578, 0.5623594327415111, 0.1842114982206946});
        atest = DoubleFixedMatrix.createInstance(9, 8, new double[]{0.5651248129845285, 0.656931969814907, -1.0158033999395344, -0.8995837123363661, 0.29728053677880595, 1.3038203704580624, 0.7646517269577205, -0.9313268105410196, -1.6723993898266338, -0.6580196077227309, 2.227049310889921, 0.11984258692028799, -0.1534178309411659, -1.0800913637563552, -1.2911371427920577, 1.087341185325378, -0.741848212549783, -0.2656940184906117, 3.354720000490959, 0.8359897761426531, -0.03310206023488216, -2.486607965700329, -2.266342196997149, -0.5410593187789258, -0.37119347458669577, 0.6765836795734356, 0.28339145178817304, -0.2963053877609101, -0.35492598097545175, -0.5578887266646835, 0.4136904138223644, -0.10734115749658762, 1.8543107193763981, 1.0289404962465845, -4.800504114909982, -0.9662453923437602, 0.0486964982575252, 4.828260873314706, 2.608758649647998, -0.8475339273385897, 1.5586720160411456, 1.1262872692908403, -2.18064825930433, 0.2591941350219291, -0.36264575116770426, 0.5287825446915624, 0.9803770087981467, 0.08656891456454763, -3.0266857151042075, -0.9188036441499315, 3.8564116812676734, 0.2650503907361018, 1.0569729980620135, -3.1882375483980088, -2.6614310176121285, 1.1389855048059228, 1.451237730789479, -0.8579797871228971, 0.127954557745763, 0.08471851732612831, -0.20862301110589498, 0.040971530536602324, 0.09041875841708702, 0.6097112160960524, 0.4356676229754963, -0.3105371518845968, -1.6023254379482568, 0.9435499368094239, 0.19251489318009216, 0.3364419748817017, 1.5236580896247283, -0.19639788238840913});
        assertThat(pseudoInverse(a, PseudoInverseMethodType.GREVILLE_METHOD), equalsEps(atest, 1.0E-8));
        a = DoubleFixedMatrix.createInstance(11, 7, new double[]{0.9164172108368044, 0.3370480814672847, 0.9177428436548145, 0.58387448206642, 0.48775465220172454, 0.9032096649877799, 0.9603710030312358, 0.9001887118543878, 0.338193226780238, 0.3059689212519512, 0.6325699851838295, 0.9547764629595911, 0.002606862931757603, 0.8878610795464207, 0.6869754045256988, 0.2581352105785968, 0.9976590635303851, 0.5053935139950506, 0.3801360964144743, 0.4980250347658014, 0.4286043958876392, 0.7215404870331209, 0.18390100107821306, 0.19175164616753193, 0.5427472734030602, 0.6583844107617997, 0.56206452363585, 0.023179808790297463, 0.08754481730636132, 0.6062385295699936, 0.4801808794144915, 0.06170653821101757, 0.1742609512439579, 0.593468194068669, 0.15838120853742, 0.38959766963840325, 0.9559708850230051, 0.5981448885964555, 0.18294559958757506, 0.14244582563287966, 0.4339030321146068, 0.5019584349196405, 0.9846241864856274, 0.38078742404513466, 0.40119981800121307, 0.9193335728954655, 0.05188388929641663, 0.5324152790027398, 0.2825627262016436, 0.23557621075813617, 0.602290668740977, 0.34062634912608203, 0.032660411523040334, 0.5916778621195935, 0.054071150954239866, 0.6003973167454373, 0.8524174691588307, 0.5758492587772043, 0.35378406651708716, 0.5426742264131772, 0.32888967797042734, 0.36760647314460104, 0.1644541946256547, 0.5303203034313356, 0.124673279168801, 0.9470999589792202, 0.867578544863832, 0.8849687649706725, 0.41557406123545215, 0.8341358695696437, 0.0028860726422273997, 0.6644857378886478, 0.5972800446748223, 0.8837047095855246, 0.3788324647477692, 0.18267793261881626, 0.6239912220878241});
        atest = DoubleFixedMatrix.createInstance(7, 11, new double[]{-0.036876455782758444, 0.3475912562904, 0.43894610959937963, -0.1860755487000017, -0.38116602957514517, 0.13543233792398432, 0.30111483752649953, 0.05255907645793065, 0.4406063035970342, -0.27961389613323145, -0.6738785596213899, -0.4135540929645434, 0.027601650044474485, -0.12739493104090693, 0.009962339386794028, 0.25178410204264623, 0.484839196554488, 0.035498057026428685, 0.3158295839144402, 0.3464067413176586, -0.3430059783156768, 0.27768807578812826, -0.5670626968740479, -0.3484220720571844, 1.4259017247212198, -0.4849101810755089, -0.10792347877064493, 0.12782088540138714, -0.2967958804563265, 0.010307193111147625, 0.2642151212135116, 0.4307072359667559, -0.1892317843388214, -0.20961924075379906, -0.08333288425538062, -0.29697425256325855, 0.14507189756281003, -0.14969485970940935, -0.26900110193365756, 0.48260077495564935, -0.399463070492824, -0.0033241674138980715, 0.1957507617446258, 0.7991999086404676, -0.3911130702972632, 0.18542505958525746, -0.0179674707649706, 0.8121537395262378, 0.3057338363381967, -0.2629414019756309, -0.6298774617138118, 0.33223348024747035, 0.05561114640165022, 0.35496513828083076, -0.06940218009655592, 0.9582762447438331, -0.4036943038141247, -0.8605141360875186, 0.7319741374479842, 0.6851743248220605, -0.13361806857604325, 0.020479514426598955, -0.22060627136408284, -0.45746347312405955, -0.07164573715675174, 0.016326470324072834, 1.033778046278926, 0.398303618475368, -0.7712195799580978, -0.5725568509286095, -0.19989458195213416, 0.09675269440244658, 0.16601448634802823, 0.09337685040002576, -0.58864933979397, -0.12678754425222816, 0.128296693057789});
        assertThat(pseudoInverse(a, PseudoInverseMethodType.GREVILLE_METHOD), equalsEps(atest, 1.0E-8));
        a = DoubleFixedMatrix.createInstance(8, 5, new double[]{0.10519057218406724, 0.2442366942630958, 0.02351289814330726, 0.6822814785960004, 0.36968508991330984, 0.2709121216445509, 0.12276506920199204, 0.576346555126291, 0.10117207700841746, 0.21665328750597868, 0.9525890746485207, 0.6733271218759784, 0.6281312525677131, 0.5480709003035994, 0.10600901135497154, 0.050887011780205604, 0.47551538169816576, 0.12251948930707135, 0.619740739057643, 0.19317373705216456, 0.6305822150457117, 0.31987764920492145, 0.46710575099882146, 0.42154538817743525, 0.07947369274329674, 0.42550050698362296, 0.31087827470811935, 0.6158276968598498, 0.8633771971779074, 0.545555288438832, 0.7195616308498399, 0.8648425594482657, 0.41116458287811697, 0.9357145872840562, 0.3698973479247769, 0.08853770683862816, 0.595164463706273, 0.2616544782189584, 0.22797502729588748, 0.3437106730942747});
        atest = DoubleFixedMatrix.createInstance(5, 8, new double[]{0.9864860030403414, -0.6956996804735736, 0.648427151803715, -2.692998664207352, 0.08313934889379215, -0.11565744781064058, 0.8411487344240327, -0.049851795365120743, -0.5618782045929251, -0.049325832061848374, 0.20355950395916403, 0.8525846505183601, -0.2293328032708545, -0.8134980887351507, 0.3181866768940178, 1.0952996928203145, -1.3901969986723883, 1.4008968232800696, -0.058382091846231346, 2.045872031875645, 0.45720898502250495, 0.48233422387105307, -1.0446814680870946, -0.26663322842376025, -0.04550214155018828, -0.19730425847965222, -0.18052805495680047, 1.860941595166493, 0.40316113886329397, 0.40967087187193635, -0.2480934975373513, -1.2933815717276425, 1.7493558707788142, -0.11268232372157538, -0.3914428466753502, -3.5714810694820334, -0.8811496471185799, 0.6060234141752363, 0.7694276002790912, 1.6406669354624932});
        assertThat(pseudoInverse(a, PseudoInverseMethodType.GREVILLE_METHOD), equalsEps(atest, 1.0E-8));
        a = DoubleFixedMatrix.createInstance(5, 11, new double[]{0.9861440571994611, 0.31403767803195914, 0.9516531094911731, 0.6040493695522489, 0.9548940903030753, 0.9915552892328869, 0.3043520099897954, 0.5082855022037416, 0.8603725711877591, 0.12410947586826149, 0.27821263785550143, 0.5497548247713134, 0.8489837383630324, 0.9683498495558545, 0.844663583157354, 0.5769348274949088, 0.035050043545300724, 0.41379834973119345, 0.6936405913909361, 0.5284423395523141, 0.25596916989897445, 0.26420112679337227, 0.019609375700094023, 0.0734749598315273, 0.6672150882886271, 0.3393290424130828, 0.3619882907462587, 0.3249986163270092, 0.08585845130030145, 0.9785225820367652, 0.16586163104815, 0.40289821738844545, 0.4914019678194901, 0.6777956054659517, 0.33160447274948845, 0.870453999084621, 0.10340045006425425, 0.22054482322113278, 0.3840275423701043, 0.8947138205442398, 0.35875293212701853, 0.4440352433935587, 0.30466013681413906, 0.7029934193292607, 0.1463981133546184, 0.28132284559665544, 0.5156411131972762, 0.04242239542870363, 0.019862440813274262, 0.27264916978718345, 0.5247361273647785, 0.8275064108890553, 0.0293023538755095, 0.8605615409701513, 0.5832724316650272});
        atest = DoubleFixedMatrix.createInstance(11, 5, new double[]{0.37534823070518264, 0.004417831654353506, -0.6777596885809887, -0.011096029035298327, 0.3137040468131281, -0.001476141842840839, 0.6527072010153702, -0.8309911347932704, -0.3994670671538239, 0.5879680007717557, -0.31077444061293874, 0.09028352113958271, 0.6761431831105031, 0.6005616929764531, -0.6975110891692345, -0.02470019181364975, 0.46556531632076875, 0.08954064449547978, -0.29058792034039593, -0.16221392108725177, 0.2874475521255722, 0.01565826869569094, 0.1563421341701641, -0.22718394873842637, -0.14853401977174743, 0.7015429636346947, -0.5621594225066375, -0.2104292148183691, -0.3191983370558462, 0.4852345375928885, -0.2232556696410824, 0.02501254922325149, -0.3083921102221322, 0.613657557983707, 0.062044664001162195, -0.0996571383518538, 0.02231501578519019, 0.6507738558580359, -0.223104064703811, 0.07324452639121459, 0.17390103671897605, 0.017389206992243205, -0.046740505687173646, 0.09409284288657968, -0.18296434112942733, 0.38370666655773894, 0.021160985093410606, -0.8969740951414235, -0.8036761581478048, 1.4585018447440223, -0.3873066143936815, -0.2536068665084793, 0.7396162227395943, 0.7880807570374307, -0.5436102300761338});
        assertThat(pseudoInverse(a, PseudoInverseMethodType.GREVILLE_METHOD), equalsEps(atest, 1.0E-8));
        a = DoubleFixedMatrix.createInstance(6, 5, new double[]{0.7032872879764198, 0.8161175921040057, 0.4596287494525342, 0.2836519760382975, 0.18249736494169755, 0.4506260087761611, 0.376821874292677, 0.2934792175726868, 0.43395787400844865, 0.3899560094987625, 0.8711363285807805, 0.6449934044472946, 0.8530672352932186, 0.7270464118077141, 0.4828489562972561, 0.24945740625503576, 0.4487998107083023, 0.7105975220094757, 0.9010112064090389, 0.36555103767239294, 0.7403702989711731, 0.6470626848024231, 0.2304214968855276, 0.9172322726089165, 0.595686591760101, 0.18874654393053758, 0.21511406756017293, 0.19708812989143942, 0.9896094706591492, 0.7566959994548369});
        atest = DoubleFixedMatrix.createInstance(5, 6, new double[]{-1.84660970558728, -0.5228687140818005, 1.7931637373670202, -0.9690283739402326, 2.0389973042950933, -1.566420417851966, 2.8987855558793845, 0.46078416743798767, -2.143706559530926, 0.5622187755519871, -0.9281344683893812, 0.8903678572070277, 0.07041169770476402, 0.4875777107617908, 1.0257488988603432, 0.43195622554669677, -1.7861184772689755, 0.27461383294874653, -1.5500484046079384, -1.6493394811483983, -0.16929884059087452, 1.442163305556958, 2.5367439187775718, -1.3618325886413176, 1.5311792684463836, 2.414846846272546, -0.008485057849762168, -1.8163098462055922, -3.068762925917948, 3.006427493699973});
        assertThat(pseudoInverse(a, PseudoInverseMethodType.GREVILLE_METHOD), equalsEps(atest, 1.0E-8));
        a = DoubleFixedMatrix.createInstance(7, 6, new double[]{0.48612968615334917, 0.7729001624473372, 0.8433700328045916, 0.10140221907977975, 0.12470282804544608, 0.299975194922087, 0.7852390394414538, 0.6654077517576263, 0.8931825850373967, 0.06491572772110443, 0.7214243783176169, 0.8167260377644622, 0.001365558294348701, 0.8267775822188654, 0.18843752123444302, 0.3792335407895734, 0.004445071873309114, 0.2107389757087449, 0.5251249078611326, 0.4352629835303935, 0.9723706040197946, 0.7582905498047096, 0.35626631094279404, 0.7873776978688495, 0.7115185074693102, 0.3171891208911478, 0.6065463892642581, 0.2431896262019393, 0.7045477839540498, 0.22327455708282062, 0.4346243972367122, 0.5663553755655769, 0.17145059651527306, 0.9095459971415056, 0.5266627286078653, 0.4503606074051024, 0.7459287515827251, 0.8306161412695571, 0.6832855199237282, 0.805511807859676, 0.2810476159513614, 0.2941536167882498});
        atest = DoubleFixedMatrix.createInstance(6, 7, new double[]{-0.26610991243672316, 1.5106546885518881, -2.315093415599627, -1.3988750973376394, -1.999098386953946, 0.1874324253533587, 2.710469645594838, 0.37924932328202177, 0.21729941856729101, 0.9947391023871537, -0.6975154543363189, -0.04209113747709736, 0.15059421928977568, -0.034286485359791286, 0.7520670010249707, -1.3340836198098251, 0.737079911670685, 1.3541289011013695, 1.6515943691425363, -0.8998610193486317, -1.0914706004410648, -0.23954479621740027, -0.9446104187686354, 0.10762883932811429, 0.6874538311379507, 0.33308417941682716, 0.36727455171952894, 0.13463306277024145, -0.15879457465126645, -0.7336097241631383, 1.3630947244836664, 0.26656971459928575, 2.5889729993130652, 0.3453616513798682, -1.985169268548666, -0.5791536522840274, 1.7404119916885994, -0.6821080268765543, 0.1218472539564148, -2.191874242591096, 0.456148412664654, 0.28576004503367247});
        assertThat(pseudoInverse(a, PseudoInverseMethodType.GREVILLE_METHOD), equalsEps(atest, 1.0E-8));
        a = DoubleFixedMatrix.createInstance(7, 4, new double[]{0.0028440265845349755, 0.6351739075030413, 0.5000003326671222, 0.09523263869161436, 0.7773987562093245, 0.6187986856429032, 0.9850938668323937, 0.42820754937244043, 0.2879071167283078, 0.03806571514178514, 0.9716297344792686, 0.7384577680992623, 0.5230708808050528, 0.3376431448933327, 0.5274564140088224, 0.1781089886178323, 0.29196081646259353, 0.5880885487654418, 0.49400272747210994, 0.4380077826087624, 0.2566724933140637, 0.6984309496751038, 0.32237301694391185, 0.8898917583320869, 0.9517582007743701, 0.8881326356842618, 0.70093610878366, 0.9703507802051383});
        atest = DoubleFixedMatrix.createInstance(4, 7, new double[]{-0.9438067654503408, 0.45796983706688144, -0.5012869851046701, 0.5346231117441449, -0.3046012084302796, -0.4932215769235189, 0.763708016608889, 0.8711850981736647, 0.012725856174365058, -0.7696281159453081, -0.02535803772125461, 0.37927327762202545, 0.32535625433410004, 0.029662773344208304, 0.5630782064694408, 0.3673814758902648, 0.74556043867907, 0.12180751557698957, 0.11344271785609535, -0.35418680756594745, -0.5335181242726199, -0.5041168935997397, -0.5133140774388432, 0.5645227932825843, -0.40568250058767624, -0.027565750828604508, 0.7451605413341458, 0.28047031819493773});
        assertThat(pseudoInverse(a, PseudoInverseMethodType.GREVILLE_METHOD), equalsEps(atest, 1.0E-8));
        a = DoubleFixedMatrix.createInstance(12, 6, new double[]{0.05719765926649889, 0.5907333583975914, 0.3504989493473917, 0.12507207660705955, 0.4340303190670758, 0.430469558345979, 0.019348311288212683, 0.2436812434740141, 0.9772970930902702, 0.9067932362672672, 0.7536979677994472, 0.2980870977042658, 0.790891549086564, 0.7749371399157092, 0.5762764442617365, 0.12026973557068643, 0.015053516822607471, 0.8020731190485699, 0.22157972482240407, 0.08938256048027293, 0.2716058537385315, 0.9386552662837154, 0.9982473786649091, 0.5772815874692233, 0.8439565900264199, 0.8951685065374969, 0.986182850963652, 0.48224261105773236, 0.242860814642059, 0.12270367122490466, 0.8736771682626585, 0.6490123050588263, 0.7738092099246298, 0.7420189209791348, 0.13470730844624623, 0.28594386394126825, 0.9490037920220317, 0.8959735877879809, 0.6148136561100119, 0.01301355498908785, 0.18874040742125153, 0.3362396446932424, 0.7828151004929811, 0.48155269293695513, 0.9611810754319067, 0.1670538007933683, 0.2764083245376071, 0.40379352012294845, 0.18016313378095672, 0.7251885603118872, 0.14290666125808515, 0.0756187433699067, 0.13451277181467203, 0.17612702173264516, 0.49111191854868763, 0.8013402818381048, 0.34344299691266833, 0.2783876200206642, 0.645199738272661, 0.9486339586314413, 0.03778083102783414, 0.23247467199867233, 0.5637755830331432, 0.7356436132249845, 0.9434617068312404, 0.3297815332620857, 0.16354751174656912, 0.05668757022810933, 0.3034676222773265, 0.15619191824743572, 0.5223984606496435, 0.3408329932456319});
        atest = DoubleFixedMatrix.createInstance(6, 12, new double[]{-0.6158528950547957, -0.8049173632385958, -0.2604834751751517, 0.5819412637145814, 0.14739730389366365, 0.26269844535174947, 0.662297952957411, 0.25178596769927253, -0.357120167656481, -0.07185469185208088, -0.0335607857890898, 0.30099988442793957, 0.4594584573954136, 0.006238168814557077, -0.1519808701654765, -0.2439296223139364, 0.3227070358134041, 0.02198755800063043, 0.11472676163810278, -0.540137455469857, 0.818811828159496, 0.16458398295241672, 0.06958983687250533, -0.37137560809671855, 0.22268529529158926, 0.7480562703993755, 0.24131721692740338, -0.6525352476936952, 0.04971708006123596, -0.2524120582622769, -0.24731803447211187, 0.6316415013916734, -0.29313547378697835, -0.2547561489192129, 0.013134512206024643, 0.13920557759009297, -0.22756515395183366, 0.246177079733035, 0.22807071041994073, 0.42750556516179733, 0.044200949384043675, 0.8307185519630803, -0.48595143268812657, -0.6088182342869597, 0.14423055296561024, -0.05531628922239628, -0.08831504148681452, -0.5359681567760096, 0.0404153471790048, -0.4544373270669589, -0.8552691318174027, 0.3655892407878367, 0.161865848040903, -0.5896593187999212, 0.7113833053713856, 0.20405880305820245, 0.038630356012782564, 0.04930453198018203, 0.5102001741684361, 0.581896158732899, 0.12416861036462129, 0.29132356672196014, 1.0057008492416613, -0.021167567005386828, -0.617501692031154, 0.03956452910597491, -0.5956523814854686, 0.11000502334960088, -0.30472405575859923, 0.4748769790465209, -0.3099594879634564, -0.026384848232570313});
        assertThat(pseudoInverse(a, PseudoInverseMethodType.GREVILLE_METHOD), equalsEps(atest, 1.0E-8));
        a = DoubleFixedMatrix.createInstance(6, 4, new double[]{0.30719632530193064, 0.3739532746048754, 0.1795459948432916, 0.6524678094472458, 0.020737470123970536, 0.34970746775214134, 0.11328868491043309, 0.5689932689120423, 0.13937492888246827, 0.17948980110871737, 0.7746504933421119, 0.9355105797039455, 0.5144551902864202, 0.4385792727959036, 0.9475493120882947, 0.7234098438919182, 0.829912666269347, 0.9849924936740393, 0.13409892480104524, 0.307001294974409, 0.10979760013289852, 0.4370273115246277, 0.3776319997879567, 0.8467908078088038});
        atest = DoubleFixedMatrix.createInstance(4, 6, new double[]{3.545242199554036, -2.0696564995062263, 0.5449274254481703, -0.14387304506309795, 0.192054553279309, -1.8897264485373237, -3.2635564037112816, 1.8499421369992908, -0.8752468859254106, 0.3384884729841123, 0.8834322110057933, 1.6290716155881664, -2.2659397742127254, 0.32517646942647604, 0.03579316778114899, 1.248725592028661, -0.005436543925066428, 0.42309539557509235, 2.2079584105791916, -0.27978044862395657, 0.570258163561282, -0.7109562237131658, -0.43946361399911293, -0.1956593171596489});
        assertThat(pseudoInverse(a, PseudoInverseMethodType.GREVILLE_METHOD), equalsEps(atest, 1.0E-8));
        a = DoubleFixedMatrix.createInstance(6, 9, new double[]{0.5680490686190388, 0.29340968705446624, 0.04002902858992807, 0.20384250544205684, 0.49744906505681374, 0.5271142047568451, 0.7210497089649666, 0.40097718314654474, 0.7301336385321093, 0.7657528987252802, 0.11256150823200517, 0.8201969632620445, 0.6562906893127471, 0.3640385621467761, 0.10373479375024186, 0.015489484823996369, 0.10233896692358091, 0.7994810374580418, 0.8318497921498581, 0.8494198325472568, 0.9793042988755081, 0.18249899199793262, 0.5492941369587971, 0.48092051301914274, 0.5448461276387238, 0.3956409486104646, 0.9370690417890272, 0.018910102812224716, 0.31446434197817097, 0.7783505584069416, 0.5715761564736057, 0.3493746545398938, 0.2331462507627603, 0.5293023261602003, 0.15633473452661284, 0.4111883087335686, 0.32539656874095424, 0.11188699417856796, 0.8660564806751319, 0.24025073808070874, 0.2787112106075914, 0.5393229462444789, 0.9335125478605418, 0.703798111398204, 0.8631447355880132, 0.998087215411485, 0.744006448794276, 0.5930741374160542, 0.8741634616685058, 0.31900559339766, 0.06148476321419616, 0.47536681545549253, 0.3233436066528096, 0.5869025682051754});
        atest = DoubleFixedMatrix.createInstance(9, 6, new double[]{-0.04599594939732432, 0.26122137731969364, 0.05082724486555108, -1.1006451512847568, 0.18927291680604702, 0.543459347552221, -0.20188759097761919, -0.8646138746827502, 0.8401949180364449, 0.3138437169637302, -0.5864459936566231, 0.40199055802474953, -1.0043541939820193, 0.16319077690830813, 0.4515752867954601, 0.18777450595971035, 0.41863403594537885, -0.1784663376806417, 0.27029361720600936, 0.2624672116556378, -0.9274261758146792, 0.757067480546641, -0.2185272411703389, 0.5375541081605687, 0.9485620375537547, 0.3377702312520674, 0.20121201575543815, 0.8942371543009463, -0.9764878258017015, -0.7236904255039927, 0.5627517568706241, 0.05689718254557641, 0.19242078477938956, 0.2863369241027075, -0.21897648272790393, -0.5523430634185279, 0.23017222521279973, -0.6547121407802314, -0.3567420895422956, 0.2853559484719662, 0.4161391817794401, 0.37830266333548823, -0.43995175682370874, -0.3647076182385132, -0.20919636602568675, -0.7960815553356708, 1.0036554098708284, 0.5580094629414967, 0.4278928758626047, 0.6376501404222792, 0.04816974269749724, -0.15205199611913295, -0.012300661246438832, -0.5128775200792409});
        assertThat(pseudoInverse(a, PseudoInverseMethodType.GREVILLE_METHOD), equalsEps(atest, 1.0E-8));
    }
}
