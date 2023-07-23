<div align="center"> 
  <img src="https://github.com/VoperAD/SlimeFrame/blob/main/images/SFrame-Banner.png" alt="SlimeFrame-Banner_2"width="1280" height="500"> 
</div> 

<details> 
  <summary>Table of Contents</summary> 
  <ol> 
    <li><a href="#download">Download</a></li>
    <li><a href="#about-the-addon">About The Project</a></li>
    <li><a href="#requirements">Requirements</a></li> 
    <li>
      <a href="#what-does-it-add">What does it add?</a>
      <ul>
        <li><a href="#new-resources">New Resources</li>
          <li><a href="relics">Relics</a></li>
        <li><a href="#cumulative-generators">Cumulative Generators</a></li>
        <li><a href="#auto-trader">Auto Trader</a></li>
      </ul>
    </li> 
    <li><a href="#credits"></a>Credits</li>
    <li><a href="#donate">Donate</a></li> 
  </ol> 
</details> 

## Download

- <a href="https://www.spigotmc.org/resources/slimeframe.111432/">Download from Spigot</a>

## About the addon 

SlimeFrame is a Slimefun addon inspired by Warframe that adds new machines, energy generators and a lot of other items to make your gameplay even more fun. Entre os itens adicionadas estão a Auto Trade, capaz de realizar trocas automáticas com villagers, o Item Projector, que é basicamente um holograma para itens, geradores de concreto e de lã e muito mais. Tome seu tempo para explorar todo o potencial desse addon! 

## Requirements 

Minecraft Version: 1.19+ (_it might work on servers that support lower versions as long as 1.19 is supported too_)
Slimefun Version: RC-32+

## What does it add?

### New Resources

There are a lot of new resources that are used to craft the new machines and generators. Some of them are new ores that you can get by mining minecraft ores using a Nosam Pickaxe, a special pickaxe from the addon. ***Placed blocks do not drop these ores.***

### Relics

Relics are items used to obtain Prime Components, which are components required to craft Prime-Tier machines, the best machines of the addon. 
Every relic has 3 Bronze rewards, 2 Silver rewars and 1 Gold reward (common, uncommon and rare).  There are four types of relics:

- Lith: Obtained every 50 fishes caught
- Meso: Obtained every 750 entities killed
- Neo: Obtained every 1000 blocks broken
- Axi: Obtained every 500 blocks placed

Use the command ```/sframe relics``` to check your relic inventory.
Server owners can configure these numbers in the config.yml file. 

#### How to open a relic?

Relics have two attributes that are shown in their lore: Reactants and Refinement. To open a relic you must fill it up with 10 reactants. In order to get reactants, you must put **ONE** relic in your off hand and kill enderman untill it fills up. Every enderman killed has a 25% chance to give you a reactant. When it reaches 10 reactants, simply right click with the relic in your hand and it will open and give you a reward.

***Bedrock Players:*** If you are a bedrock player you must put the relic in you first slot from the left to the right (_it is experimental and still is under testing, so if you
have any issues please make a report_).

#### Relic Refinements

Relics can be refined in order to increase the chances of gettings rarer rewards. To refine a relic use the command ```/sframe refine <refinement>```. In order to refine a relic you will need **Void Traces**, which you get every time you open a relic (you get a random amount of void traces between 1 and 20). If you want do check you many void traces you have, use the command ```/sframe traces```. There are four refinements:

- INTACT: The default refinement.
- EXCEPTIONAL: Costs 25 void traces.
- FLAWLESS: Costs 50 void traces.
- RADIANT: Costs 100 void traces.

### Cumulative Generators

Um novo tipo de gerador de energia que verifica se os blocos que estão ao seu lado, o bloco de cima ou o bloco de baixo, são também Cumulative Generators. Para cada Cumulative
Generator detectado, uma quantidade de energia, indicada por um novo atributo na descrição desses geradores chamado **Bonus Energy** será somado à energia total gerada. 
**Observação:** Os blocos que estiverem na diagonal não são contabilizados. Isso significa que o gerador de energia acumulativo checará no total 6 blocos (1 para cada lado, 1 acima e 1 abaixo).

### Auto Trader

A new machine that is capable of automatically trade with villagers. In order to make it work, you will need a ***Merchant Soul Contract**. Once you have a contract, choose a villager and right click it with the contract in your hand. It will kill the villager and fill the contract with its trades. After that, put the contract in the Auto Trader and select one of the trades.

**Automatizando o farm dos novos ores:** This machine can be used to automatically produce the new ores that you get using the Nosam Pickaxe. Every time a villager gets a new trade, há uma pequena chance de que venha uma troca por um dos minérios novos. Nesse caso, use o merchant soul contract novamente e use a auto trader com esse novo contrato.

## Credits

Thanks to <a href="https://minecraft-heads.com/">Minecraft Heads</a> for the heads the addon uses.
A big thanks to all the people that helped my in the programming channel in the Slimefun Discord Server.
Thanks to the server **AbsolutGG** for letting me test the addon.

## Donate

If you want to support the project and make someones day, you can donate on <a href="https://ko-fi.com/voper">Ko-Fi</a> 🙂
